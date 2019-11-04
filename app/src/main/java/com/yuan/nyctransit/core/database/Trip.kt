package com.yuan.nyctransit.core.database

import android.content.Context
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@Entity(tableName = "trip")
@JsonClass(generateAdapter = true)
data class Trip(
    @ColumnInfo("route_id") @Json(name = "route_id") var routeId: String,
    @ColumnInfo("service_id") @Json(name = "service_id") var serviceId: String,
    @PrimaryKey @ColumnInfo("trip_id") @Json(name = "trip_id") var tripId: String,
    @ColumnInfo("trip_headsign") @Json(name = "trip_headsign") var tripHeadsign: String,
    @ColumnInfo("trip_short_name") @Json(name = "trip_short_name") var tripShortName: String,
    @ColumnInfo("direction_id") @Json(name = "direction_id") var directionId: String,
    @ColumnInfo("shape_id") @Json(name = "shape_id") var shapeId: String?
)

fun Trip.saveToDB(context: Context) = LirrGtfsBase.getInstance(context)!!.tripDao().insert(this)
