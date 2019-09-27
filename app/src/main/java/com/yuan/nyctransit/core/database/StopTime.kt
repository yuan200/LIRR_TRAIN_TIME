package com.yuan.nyctransit.core.database

data class StopTime(var tripId : String,
                    var arrivarTime : String,
                    var departureTime : String,
                    var stopId : String,
                    var stopSequence : Int) {
}