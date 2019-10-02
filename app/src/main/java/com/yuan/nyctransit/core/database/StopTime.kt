package com.yuan.nyctransit.core.database

import com.google.gson.annotations.SerializedName

data class StopTime(
    @SerializedName("trip_id") var tripId: String,
    @SerializedName("arrival_time") var arrivalTime: String,
    @SerializedName("departure_time") var departureTime: String,
    @SerializedName("stop_id") var stopId: String,
    @SerializedName("stop_sequence") var stopSequence: Int
) {
}