package com.yuan.nyctransit.core.database

import com.google.gson.annotations.SerializedName

data class Route(
    @SerializedName("route_id") var routeId: String,
    @SerializedName("route_short_name") var routeShortName: String?,
    @SerializedName("route_long_name") var routeLongName: String,
    @SerializedName("route_type") var routeType: String,
    @SerializedName("route_color") var routeColor: String,
    @SerializedName("route_text_color") var routeTextColor: String
) {
}