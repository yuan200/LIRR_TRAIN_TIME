package com.yuan.nyctransit.features.lirr

import com.yuan.nyctransit.core.database.Stop
import javax.inject.Inject

class LirrRoomService @Inject constructor() {
    val lirrFeedBase: LirrBase
    fun allStops(): List<Stop>
}