package com.yuan.nyctransit.features.lirr

import com.yuan.nyctransit.core.database.LirrGtfsBase
import com.yuan.nyctransit.core.database.Stop
import javax.inject.Inject

class LirrRoomService
@Inject constructor(private val lirrGtfsBase: LirrGtfsBase) {


    suspend fun allStops(): List<Stop> = lirrGtfsBase.stopDao().allStops()
}