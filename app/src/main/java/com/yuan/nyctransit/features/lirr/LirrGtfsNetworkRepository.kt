package com.yuan.nyctransit.features.lirr

import android.content.Context
import com.yuan.nyctransit.core.database.Stop
import com.yuan.nyctransit.core.database.saveToDB
import com.yuan.nyctransit.core.exception.Failure
import com.yuan.nyctransit.core.functional.Either
import com.yuan.nyctransit.core.platform.NetworkHandler
import retrofit2.Call
import timber.log.Timber
import javax.inject.Inject

class LirrGtfsNetworkRepository
@Inject constructor(private val networkHandler: NetworkHandler,
                    private val service: LirrGtfsService
): LirrGtfsRepository {

    override fun allStops(): Either<Failure, List<Stop>> {
        throw UnsupportedOperationException()
    }

    override fun lirrGtfs(context: Context): Either<Failure, LirrGtfs> {
        Timber.d("calling lirr Gtfs...")
        return when (networkHandler.isConnected) {
            //todo this transform doesn't look right
            true -> request(service.getLirrGtfs(), {
                it.saveToDB(context)
                it.gtfs!!.stops.forEach {
                    it.saveToDB(context)
                }
                it.gtfs!!.stopTimes.forEach {
                    it.saveToDB(context)
                }
                it.gtfs!!.trips.forEach {
                    it.saveToDB(context)
                }
                it}, LirrGtfs.empty())
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
        }
    }
}