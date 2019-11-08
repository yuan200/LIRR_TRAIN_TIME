package com.yuan.nyctransit.extenstion

import android.content.Context
import com.google.transit.realtime.GtfsRealtime
import com.yuan.nyctransit.core.database.LirrGtfsBase
import com.yuan.nyctransit.features.lirr.LirrFeed
import com.yuan.nyctransit.features.lirr.StopTimeUpdateView
import kotlinx.coroutines.*
import okhttp3.ResponseBody
import org.threeten.bp.LocalDate
import org.threeten.bp.LocalDateTime
import timber.log.Timber
import java.io.IOException
import java.io.InputStream


fun ResponseBody.responseBodyToStopTimeUpdateView(
    context: Context,
    stopId: String
): MutableList<StopTimeUpdateView> {
    try {
        var inputStream: InputStream? = null

        try {
            val fileReader = ByteArray(4096)

            inputStream = this.byteStream()

            while (true) {

                val feed: GtfsRealtime.FeedMessage = GtfsRealtime.FeedMessage.parseFrom(inputStream)
                LirrFeed.entityList = feed.entityList

                var stopTimeUpdateViewList = mutableListOf<StopTimeUpdateView>()

                runBlocking {

                //stop time from real time api
                for (entity in LirrFeed.entityList) {
                    for (stopTimeUpdate in entity.tripUpdate.stopTimeUpdateList) {
                        if (stopId == stopTimeUpdate.stopId) {
                            var stopName = ""
                            var tripHeadSign = ""
                            var tripId = entity.tripUpdate.trip.tripId
                            val db = LirrGtfsBase.getInstance(context)
                            //todo too many job, can them combine in one coroutineScope?
                            val job = CoroutineScope(Dispatchers.IO).async {
                                db?.stopDao()?.getStop(stopId)!!.stopName
                            }

                            val tripJob = CoroutineScope(Dispatchers.IO).async {
                                db?.tripDao()?.getByTripId(tripId)!!.tripHeadsign
                            }

                            val stopTimeJob = CoroutineScope(Dispatchers.IO).async {
                                db?.stopTimeDao()!!.getArrivalTime(tripId, stopId).arrivalTime
                            }
                            CoroutineScope(Dispatchers.Main).launch {
                                stopName = job.await()
                                tripHeadSign = tripJob.await()
                                val arrivalTime = stopTimeJob.await()

                                val stopTimeUpdateView = StopTimeUpdateView(
                                    stopName,
                                    "",
                                    "",
                                    arrivalTime,
                                    null,
                                    stopTimeUpdate.arrival.delay,
                                    tripHeadSign
                                )
                                stopTimeUpdateViewList.add(stopTimeUpdateView)
                            }
                        }
                    }
                }

                //schedule time from local database
                val hashMap = HashMap<String, StopTimeUpdateView>()
                val dbJob = CoroutineScope(Dispatchers.IO).launch {
                    val db = LirrGtfsBase.getInstance(context)
                    val today = LocalDate.now().toDateString()
                    val resultList = db?.stopTimeDao()?.getStopTimeByStop(stopId, today)
                    resultList?.forEach {
                        val tripHeadSignJob = db?.tripDao()!!.getByTripId(it.tripId)!!.tripHeadsign
                        var stopName = db?.stopDao()!!.getStop(stopId).stopName
                        val routeId = db?.tripDao()!!.getRouteId(it.tripId)
                        val routeColor = db?.routeDao().getRouteColor(routeId)
                        val stopTimeUpdateView = StopTimeUpdateView(
                            stopName,
                            "",
                            "",
                            it.arrivalTime,
                            null,
                            0,
                            tripHeadSignJob,
                            routeColor
                        )
                        //todo use a offset so it could show delay train
                        if (!stopTimeUpdateView.arrivingTime!!.isBefore(LocalDateTime.now())) {
                            hashMap[stopTimeUpdateView.arrivingTimeStr + stopTimeUpdateView.tripHeadSign] =
                                stopTimeUpdateView
                        }
                    }
                    //update schedule time according to the delay from api
                    stopTimeUpdateViewList.forEach {
                        val key = it.arrivingTimeStr + it.tripHeadSign
                        if (hashMap.containsKey(key)) {
                            hashMap[key].apply {
                                this!!.delay = it.delay
                            }
                        }
                    }
                    stopTimeUpdateViewList = mutableListOf()
                    hashMap.forEach { _, stopTimeView -> stopTimeUpdateViewList.add(stopTimeView) }
                    stopTimeUpdateViewList.sortBy { stopTimeUpdateView -> stopTimeUpdateView.arrivingTime }
                    LirrFeed.stopTimeUpdateViewList = stopTimeUpdateViewList
                    Timber.i("end of coroutine")
                }
                    dbJob.join()

            }
                Timber.i("this should after coroutine")

                val read = inputStream.read(fileReader)
                if (read == -1) break

            }
            return LirrFeed.stopTimeUpdateViewList

        } catch (e: IOException) {
            return mutableListOf()
        } finally {
            inputStream ?: inputStream!!.close()
        }

    } catch (ex: IOException) {
        return mutableListOf()
    }
}