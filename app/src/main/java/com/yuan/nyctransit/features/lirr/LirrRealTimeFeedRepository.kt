package com.yuan.nyctransit.features.lirr

import android.content.Context
import com.yuan.nyctransit.core.exception.Failure
import com.yuan.nyctransit.core.functional.Either

interface LirrRealTimeFeedRepository {
    fun lirrFeed(
        context: Context,
        stopId: String
    ): Either<Failure, MutableList<StopTimeUpdateView>>

}