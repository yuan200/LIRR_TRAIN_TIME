package com.yuan.nyctransit.core.database

import android.content.Context
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "trip")
data class Trip(
    @ColumnInfo("route_id") @SerializedName("route_id") var routeId: String,
    @ColumnInfo("service_id") @SerializedName("service_id") var serviceId: String,
    @PrimaryKey @ColumnInfo("trip_id") @SerializedName("trip_id") var tripId: String,
    @ColumnInfo("trip_headsign") @SerializedName("trip_headsign") var tripHeadsign: String,
    @ColumnInfo("trip_short_name") @SerializedName("trip_short_name") var tripShortName: String,
    @ColumnInfo("direction_id") @SerializedName("direction_id") var directionId: String,
    @ColumnInfo("shape_id") @SerializedName("shape_id") var shapeId: String
)

fun Trip.saveToDB(context: Context) = LirrGtfsBase.getInstance(context)!!.tripDao().insert(this)
