package com.yuan.nyctransit.core.database

data class Stop(val stopId : String,
                val stopName : String,
                val stopDesc : String,
                val stopLat : String,
                val stopLon : String,
                val stopUrl : String,
                val wheelchairBoarding : Int) {
}