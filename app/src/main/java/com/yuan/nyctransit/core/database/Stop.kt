package com.yuan.nyctransit.core.database

data class Stop(var stopId : String,
                var stopName : String,
                var stopDesc : String,
                var stopLat : String,
                var stopLon : String,
                var stopUrl : String,
                var wheelchairBoarding : Int) {
}