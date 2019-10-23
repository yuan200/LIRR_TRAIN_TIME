package com.yuan.nyctransit.core.database

import androidx.room.TypeConverter
import java.text.SimpleDateFormat
import java.util.*

class DateTimeConverters {

    val dateTimePattern = "yyyy-MM-dd'T'HH:mm:ssZ"

    val datePattern = "HH:mm:ss"

    @TypeConverter
    fun fromStringToDataTime(str: String): Date {
        val simpleDateFormat: SimpleDateFormat
        if (str.contains("T")) simpleDateFormat = SimpleDateFormat(dateTimePattern)
        else simpleDateFormat = SimpleDateFormat(datePattern)

        return simpleDateFormat.parse(str)
    }

    @TypeConverter
    fun fromDateTimeToString(date: Date): String {
        val simpleDateFormat: SimpleDateFormat
        if (date.toString().length > 8) simpleDateFormat = SimpleDateFormat(dateTimePattern)
        else simpleDateFormat = SimpleDateFormat(datePattern)

        return simpleDateFormat.format(date)
    }


}