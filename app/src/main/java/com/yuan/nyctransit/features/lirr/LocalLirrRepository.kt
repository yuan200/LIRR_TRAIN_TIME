package com.yuan.nyctransit.features.lirr

import com.yuan.nyctransit.core.database.LirrGtfsBase
import com.yuan.nyctransit.core.database.Stop
import javax.inject.Inject

class LirrRoomRepository
@Inject constructor(val lirrGtfsBase: LirrGtfsBase) : ILocalLirrRepository {
    //todo should return Either<Failure, List<stop>>
    override suspend fun getAllStops(): List<Stop> = lirrGtfsBase.stopDao().allStops()
}