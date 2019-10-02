package com.yuan.nyctransit.core.database

import com.google.gson.annotations.SerializedName

data class Stop(
    @SerializedName("stop_id") var stopId: String,
    @SerializedName("stop_name") var stopName: String,
    @SerializedName("stop_desc") var stopDesc: String,
    @SerializedName("stop_lat") var stopLat: String,
    @SerializedName("stop_lon") var stopLon: String,
    @SerializedName("stop_url") var stopUrl: String,
    @SerializedName("wheelchair_boarding") var wheelchairBoarding: Int
)
