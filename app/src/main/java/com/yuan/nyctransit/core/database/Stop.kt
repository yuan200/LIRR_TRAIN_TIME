package com.yuan.nyctransit.core.database

import android.content.Context
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@Entity(tableName = "stop")
@JsonClass(generateAdapter = true)
data class Stop(
    @PrimaryKey @ColumnInfo("stop_id") @Json(name = "stop_id") var stopId: String,
    @ColumnInfo("stop_name") @Json(name = "stop_name") var stopName: String,
    @ColumnInfo("stop_desc") @Json(name = "stop_desc") var stopDesc: String,
    @ColumnInfo("stop_lat") @Json(name = "stop_lat") var stopLat: String,
    @ColumnInfo("stop_lon") @Json(name = "stop_lon") var stopLon: String,
    @ColumnInfo("stop_url") @Json(name = "stop_url") var stopUrl: String,
    @ColumnInfo("wheelchair_boarding") @Json(name = "wheelchair_boarding") var wheelchairBoarding: Int
) {
    companion object {
        fun empty():Stop = Stop("","","","","","",0)
    }
}

fun Stop.saveToDB(context: Context) {
    val db = LirrGtfsBase.getInstance(context)
    db!!.stopDao().insert(this)
}
