package com.yuan.nyctransit.core.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.JsonClass

@Entity(tableName = "route")
@JsonClass(generateAdapter = true)
data class Route(
    @PrimaryKey var route_id: String,
    var route_short_name: String?,
    var route_long_name: String,
    var route_type: String,
    var route_color: String,
    var route_text_color: String
) {
}