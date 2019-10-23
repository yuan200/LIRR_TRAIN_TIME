package com.yuan.nyctransit.extenstion

import android.content.Context
import com.google.transit.realtime.GtfsRealtime
import com.yuan.nyctransit.core.database.LirrGtfsBase
import com.yuan.nyctransit.features.lirr.LirrFeed
import com.yuan.nyctransit.features.lirr.StopTimeUpdateView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import okhttp3.ResponseBody
import timber.log.Timber
import java.io.*
import java.time.LocalTime
import java.time.format.DateTimeFormatter


fun ResponseBody.writeResponseBodyToDisk(
    context: Context,
    stopId: String
): MutableList<StopTimeUpdateView> {
    try {
        val filename = "lirrFeeder"
        val file = File(context.filesDir, filename)

        var inputStream: InputStream? = null
        var outputStream: OutputStream? = null

        try {
            val fileReader = ByteArray(4096)

            val fileSize = this.contentLength()
            var fileSizeDownload = 0

            inputStream = this.byteStream()
            outputStream = FileOutputStream(file)

            while (true) {

                val feed: GtfsRealtime.FeedMessage = GtfsRealtime.FeedMessage.parseFrom(inputStream)
                LirrFeed.entityList = feed.entityList

                val stopTimeUpdateViewList = mutableListOf<StopTimeUpdateView>()

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
                            Timber.i(tripId)
                            CoroutineScope(Dispatchers.Main).launch {
                                stopName = job.await()
                                tripHeadSign = tripJob.await()
                                val arrivalTime = stopTimeJob.await()

                                val stopTimeUpdateView = StopTimeUpdateView(
                                    stopName, "",
                                    "", arrivalTime, stopTimeUpdate.arrival.delay, tripHeadSign
                                )
                                stopTimeUpdateViewList.add(stopTimeUpdateView)
                            }
                        }
                    }
                }
                LirrFeed.stopTimeUpdateViewList = stopTimeUpdateViewList

                val read = inputStream.read(fileReader)
                if (read == -1) break

                outputStream.write(fileReader, 0, read)
                fileSizeDownload += read

                Timber.d("file downlaed: $fileSizeDownload of $fileSize")
            }
            outputStream.flush()
            return LirrFeed.stopTimeUpdateViewList

        } catch (e: IOException) {
            return mutableListOf()
        } finally {
            inputStream ?: inputStream!!.close()
            outputStream ?: outputStream!!.close()
        }

    } catch (ex: IOException) {
        return mutableListOf()
    }
}