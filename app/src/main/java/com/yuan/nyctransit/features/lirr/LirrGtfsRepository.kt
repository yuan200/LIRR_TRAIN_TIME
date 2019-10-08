package com.yuan.nyctransit.features.lirr

import android.content.Context
import com.yuan.nyctransit.core.database.Stop
import com.yuan.nyctransit.core.exception.Failure
import com.yuan.nyctransit.core.functional.Either

interface LirrGtfsRepository {
    fun lirrGtfs(context: Context): Either<Failure, LirrGtfs>

    fun allStops(): Either<Failure, List<Stop>>

}