package com.yuan.nyctransit.core.database

import android.content.Context
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "stop")
data class Stop(
    @PrimaryKey(autoGenerate = true) var id: Long?,
    @ColumnInfo("stop_id") @SerializedName("stop_id") var stopId: String,
    @ColumnInfo("stop_name") @SerializedName("stop_name") var stopName: String,
    @ColumnInfo("stop_desc") @SerializedName("stop_desc") var stopDesc: String,
    @ColumnInfo("stop_lat") @SerializedName("stop_lat") var stopLat: String,
    @ColumnInfo("stop_lon") @SerializedName("stop_lon") var stopLon: String,
    @ColumnInfo("stop_url") @SerializedName("stop_url") var stopUrl: String,
    @ColumnInfo("wheelchair_boarding") @SerializedName("wheelchair_boarding") var wheelchairBoarding: Int
) {
    companion object {
        fun empty():Stop = Stop(0,"","","","","","",0)
    }
}

fun Stop.saveToDB(context: Context) {
    val db = LirrGtfsBase.getInstance(context)
    db!!.stopDao().insert(this)
}
