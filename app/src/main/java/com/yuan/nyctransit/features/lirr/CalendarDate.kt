package com.yuan.nyctransit.features.lirr

import android.content.Context
import androidx.room.Entity
import com.squareup.moshi.JsonClass
import com.yuan.nyctransit.core.database.LirrGtfsBase

@Entity(tableName = "calendar_date", primaryKeys = ["service_id", "date"])
@JsonClass(generateAdapter = true)
class CalendarDate(
    var service_id: String,
    var date: String,
    var exception_type: String
)

fun CalendarDate.saveToDB(context: Context) {
    val db = LirrGtfsBase.getInstance(context)
    db!!.calendarDateDao().insert(this)
}
