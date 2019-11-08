package com.yuan.nyctransit.features.lirr

import android.content.Context
import com.yuan.nyctransit.core.database.DateTimeConverters
import com.yuan.nyctransit.core.database.LirrGtfsBase
import com.yuan.nyctransit.core.database.Stop
import com.yuan.nyctransit.core.database.saveToDB
import com.yuan.nyctransit.core.exception.Failure
import com.yuan.nyctransit.core.functional.Either
import com.yuan.nyctransit.core.platform.NetworkHandler
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.Channel
import retrofit2.Call
import timber.log.Timber
import javax.inject.Inject

class LirrGtfsNetworkRepository
@Inject constructor(
    private val networkHandler: NetworkHandler,
    private val service: LirrGtfsService
) : LirrGtfsRepository {

    override fun allStops(): Either<Failure, List<Stop>> {
        throw UnsupportedOperationException()
    }

    override fun lirrGtfs(context: Context, channel: Channel<Int>): Either<Failure, LirrGtfs> {
        Timber.d("calling lirr Gtfs...")
        return when (networkHandler.isConnected) {
            //todo this transform doesn't look right
            true -> request(service.getLirrGtfs(), {
                val db = LirrGtfsBase.getInstance(context)
                val job = CoroutineScope(Dispatchers.Default).async {
                    channel.send(10)
                    db?.lirrGtfsDao()?.getRevised()
                }
                runBlocking {
                val dbJob = CoroutineScope(Dispatchers.IO).launch {
                    val oldRevised = job.await()
                    val convrter = DateTimeConverters()
                    val latestRevisied = it.revised
                    if (oldRevised.isNullOrEmpty() || latestRevisied.after(
                            convrter.fromStringToDataTime(oldRevised)
                        )
                    ) {
                        it.saveToDB(context)
                        channel.send(30)
                        it.gtfs!!.stops.forEach {
                            it.saveToDB(context)
                        }
                        channel.send(40)
                        it.gtfs!!.stop_times.forEach {
                            launch {
                                it.saveToDB(context)
                            }
                        }
                        it.gtfs!!.routes.forEach {
                            it.saveToDB(context)
                        }
                        channel.send(60)
                        it.gtfs!!.trips.forEach {
                            launch {
                                it.saveToDB(context)
                            }
                        }
                        channel.send(70)
                        it.gtfs!!.calendar_dates.forEach {
                            launch {
                                it.saveToDB(context)
                            }
                        }
                        channel.send(90)
                    }
                }
                    dbJob.join()
                    channel.send(100)
                }
                it
            }, LirrGtfs.empty())
            false, null -> Either.Left(Failure.ServerError)
        }
    }

    private fun <T, R> request(call: Call<T>, transform: (T) -> R, default: T): Either<Failure, R> {
        return try {
            val response = call.execute()
            when (response.isSuccessful) {
                true -> Either.Right(
                    transform(
                        (response.body() ?: default)
                    )
                )
                false -> Either.Left(Failure.ServerError)
            }
        } catch (exception: Throwable) {
            Either.Left(Failure.ServerError)
            throw exception
        }
    }
}