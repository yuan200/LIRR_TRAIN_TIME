package com.yuan.nyctransit.core.database

import android.content.Context
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import com.yuan.nyctransit.features.lirr.LirrGtfs
import org.threeten.bp.LocalTime
import java.util.*

@Entity(tableName = "stop_time", primaryKeys = ["trip_id", "stop_id"])
@JsonClass(generateAdapter = true)
data class StopTime(
    @ColumnInfo("trip_id") @Json(name = "trip_id") var tripId: String,
    @ColumnInfo("arrival_time") @Json(name = "arrival_time") var arrivalTime: String,
    @ColumnInfo("departure_time") @Json(name = "departure_time") var departureTime: String,
    @ColumnInfo("stop_id") @Json(name = "stop_id") var stopId: String,
    @ColumnInfo("stop_sequence") @Json(name = "stop_sequence") var stopSequence: Int
)

fun StopTime.saveToDB(context: Context) = LirrGtfsBase.getInstance(context)!!.stopTimeDao().insert(this)

