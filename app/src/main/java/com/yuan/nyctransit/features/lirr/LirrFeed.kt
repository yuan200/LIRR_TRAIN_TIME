package com.yuan.nyctransit.features.lirr

import com.google.transit.realtime.GtfsRealtime

object LirrFeed {
    var entityList: List<GtfsRealtime.FeedEntity> = emptyList()

    var stopTimeUpdateList  = mutableListOf<GtfsRealtime.TripUpdate.StopTimeUpdate>()

    fun getvoid() {

    }
}