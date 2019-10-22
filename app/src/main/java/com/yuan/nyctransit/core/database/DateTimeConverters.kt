package com.yuan.nyctransit.core.database

import androidx.room.TypeConverter
import java.text.SimpleDateFormat
import java.util.*

class DateTimeConverters {

    val dateTimePattern = "yyyy-MM-dd'T'HH:mm:ssZ"


    @TypeConverter
    fun fromStringToDataTime(str: String): Date {
        val simpleDateFormat = SimpleDateFormat(dateTimePattern)
        return simpleDateFormat.parse(str)

    }

    @TypeConverter
    fun fromDateTimeToString(date: Date): String {
        val simpleDateFormat = SimpleDateFormat(dateTimePattern)
        return simpleDateFormat.format(date)
    }


}