package com.yuan.nyctransit.core.database

import com.google.gson.annotations.SerializedName

data class Trip(
    @SerializedName("route_id") var routeId: String,
    @SerializedName("service_id") var serviceId: String,
    @SerializedName("trip_headsign") var tripHeadsign: String,
    @SerializedName("trip_short_name") var tripShortName: String,
    @SerializedName("direction_id") var directionId: String,
    @SerializedName("shape_id") var shapeId: String
)
