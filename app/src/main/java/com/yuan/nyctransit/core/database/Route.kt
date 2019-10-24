package com.yuan.nyctransit.core.database

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Route(
    @Json(name = "route_id") var routeId: String,
    @Json(name = "route_short_name") var routeShortName: String?,
    @Json(name = "route_long_name") var routeLongName: String,
    @Json(name = "route_type") var routeType: String,
    @Json(name = "route_color") var routeColor: String,
    @Json(name = "route_text_color") var routeTextColor: String
) {
}