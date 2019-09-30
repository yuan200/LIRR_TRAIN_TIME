package com.yuan.nyctransit.core.database

import com.google.gson.annotations.SerializedName

data class Shape(
    @SerializedName("shape_id") var shapeId: String,
    @SerializedName("shape_pt_lat") var shapePtLat: String,
    @SerializedName("shape_pt_lon") var shapePtLon: String,
    @SerializedName("shape_pt_sequence") var shapePtSequence: Int
)
