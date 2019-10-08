package com.yuan.nyctransit.core.database

import android.content.Context
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import com.yuan.nyctransit.features.lirr.LirrGtfs

@Entity(tableName = "stop_time")
data class StopTime(
    @PrimaryKey(autoGenerate = true) var id: Long?,
    @ColumnInfo("trip_id") @SerializedName("trip_id") var tripId: String,
    @ColumnInfo("arrival_time") @SerializedName("arrival_time") var arrivalTime: String,
    @ColumnInfo("departure_time") @SerializedName("departure_time") var departureTime: String,
    @ColumnInfo("stop_id") @SerializedName("stop_id") var stopId: String,
    @ColumnInfo("stop_sequence") @SerializedName("stop_sequence") var stopSequence: Int
)

fun StopTime.saveToDB(context: Context) = LirrGtfsBase.getInstance(context)!!.stopTimeDao().insert(this)

