package com.yuan.nyctransit.core.database

data class StopTime(val tripId : String,
                    val arrivalTime : String,
                    val departureTime : String,
                    val stopId : String,
                    val stopSequence : Int) {
}