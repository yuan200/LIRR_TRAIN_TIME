package com.yuan.nyctransit.core.database

import androidx.room.TypeConverter
import java.text.SimpleDateFormat
import java.util.*

class DateTimeConverters {

    val pattern = "yyyy-MM-dd'T'HH:mm:ssZ"

    @TypeConverter
    fun fromStringToDataTime(str: String): Date {
        //todo requires api 26(current 24)
        val simpleDateFormat = SimpleDateFormat(pattern)
        return simpleDateFormat.parse(str)

    }

    @TypeConverter
    fun fromDateTimeToString(date: Date): String {
        //todo requires api 26(current 24)
        val simpleDateFormat = SimpleDateFormat(pattern)
        return simpleDateFormat.format(date)
    }
}