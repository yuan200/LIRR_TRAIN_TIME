package com.yuan.nyctransit.core.database

import androidx.room.TypeConverter
import java.text.SimpleDateFormat
import java.util.*

class DateConverters {

    val datePattern = "HH:mm:ss"

    @TypeConverter
    fun fromStringToDate(str: String): Date {
        val simpleDateFormat = SimpleDateFormat(datePattern)
        return simpleDateFormat.parse(str)
    }

    @TypeConverter
    fun fromDateToString(date: Date): String {
        val simpleDateFormat = SimpleDateFormat(datePattern)
        return simpleDateFormat.format(date)
    }
}