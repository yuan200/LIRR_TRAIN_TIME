package com.yuan.nyctransit.features.lirr

import android.content.Context
import com.yuan.nyctransit.core.database.Stop
import com.yuan.nyctransit.core.exception.Failure
import com.yuan.nyctransit.core.functional.Either
import kotlinx.coroutines.channels.Channel

interface LirrGtfsRepository {
    fun lirrGtfs(context: Context, channel: Channel<Int>): Either<Failure, LirrGtfs>

    fun allStops(): Either<Failure, List<Stop>>

}