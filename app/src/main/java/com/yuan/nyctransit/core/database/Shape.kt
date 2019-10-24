package com.yuan.nyctransit.core.database

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Shape(
    @Json(name = "shape_id") var shapeId: String,
    @Json(name = "shape_pt_lat") var shapePtLat: String,
    @Json(name = "shape_pt_lon") var shapePtLon: String,
    @Json(name = "shape_pt_sequence") var shapePtSequence: Int
)
