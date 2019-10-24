package com.yuan.nyctransit.core.database

import androidx.room.TypeConverter
import org.threeten.bp.LocalTime
import org.threeten.bp.format.DateTimeFormatter

//todo move to a different package
class LocalTimeConverters {

    @TypeConverter
    fun fromStringToLocalTime(str: String): LocalTime =
        LocalTime.parse(str, DateTimeFormatter.ofPattern("HH:mm:ss"))


    @TypeConverter
    fun fromLocalTimeToString(localTime: LocalTime): String {
        val formatter = DateTimeFormatter.ofPattern("HH:mm:ss")
        return localTime.format(formatter)
    }
}