package com.yuan.nyctransit.features.lirr

import android.content.Context
import com.yuan.nyctransit.core.database.Stop
import com.yuan.nyctransit.core.exception.Failure
import com.yuan.nyctransit.core.functional.Either

class RoomLirrFeedRepository(private val service: LirrRoomService): LirrGtfsRepository {

    override fun lirrGtfs(context: Context): Either<Failure, LirrGtfs> {
        throw UnsupportedOperationException()
    }

    override fun allStops(): Either<Failure, List<Stop>> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}


