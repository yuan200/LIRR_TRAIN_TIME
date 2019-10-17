package com.yuan.nyctransit.features.lirr

import android.content.Context
import com.google.transit.realtime.GtfsRealtime
import com.yuan.nyctransit.UseCase
import com.yuan.nyctransit.core.exception.Failure
import com.yuan.nyctransit.core.functional.Either
import javax.inject.Inject

class GetLirrFeed
@Inject constructor(
    private val lirrFeedRepository: LirrFeedRepository,
    private val context: Context
) : UseCase<MutableList<StopTimeUpdateView>, Boolean>() {

    var stopId: String = ""

    // this Boolean is just a place holder, it doesn't make any sense
    override suspend fun run(params: Boolean): Either<Failure, MutableList<StopTimeUpdateView>> {
        if (stopId.isEmpty()) throw IllegalStateException("stopId can not be empty")
        return lirrFeedRepository.lirrFeed(context, stopId)
    }

}