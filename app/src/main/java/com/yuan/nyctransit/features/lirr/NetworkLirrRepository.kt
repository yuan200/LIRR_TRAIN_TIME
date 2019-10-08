package com.yuan.nyctransit.features.lirr

import android.content.Context
import com.yuan.nyctransit.core.exception.Failure
import com.yuan.nyctransit.core.functional.Either
import com.yuan.nyctransit.core.platform.NetworkHandler
import com.yuan.nyctransit.extenstion.writeResponseBodyToDisk
import okhttp3.MediaType
import okhttp3.ResponseBody
import retrofit2.Call
import timber.log.Timber
import javax.inject.Inject

class NetworkLirrRepository
@Inject constructor(private val networkHandler: NetworkHandler,
                    private val service: LirrService
) : LirrFeedRepository {

    override fun lirrFeed(context: Context): Either<Failure, Boolean> {
        Timber.d("calling lirrFeed...")
        return when (networkHandler.isConnected) {
            true -> request(service.lirrFeed(), { it.writeResponseBodyToDisk(context)},
                ResponseBody.create(
                    MediaType.parse("Jason"), toString()
                )
            )
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