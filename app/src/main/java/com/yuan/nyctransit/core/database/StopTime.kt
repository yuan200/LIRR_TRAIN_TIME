package com.yuan.nyctransit.core.database

import android.content.Context
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import com.yuan.nyctransit.features.lirr.LirrGtfs
import org.threeten.bp.LocalTime
import java.util.*

@Entity(tableName = "stop_time", primaryKeys = ["trip_id", "stop_id"])
data class StopTime(
    @ColumnInfo("trip_id") @SerializedName("trip_id") var tripId: String,
    @ColumnInfo("arrival_time") @SerializedName("arrival_time") var arrivalTime: String,
    @ColumnInfo("departure_time") @SerializedName("departure_time") var departureTime: String,
    @ColumnInfo("stop_id") @SerializedName("stop_id") var stopId: String,
    @ColumnInfo("stop_sequence") @SerializedName("stop_sequence") var stopSequence: Int
)

fun StopTime.saveToDB(context: Context) = LirrGtfsBase.getInstance(context)!!.stopTimeDao().insert(this)

