package com.yuan.nyctransit.core.database

import androidx.room.TypeConverters
import org.threeten.bp.LocalTime
import org.threeten.bp.format.DateTimeFormatter
import org.threeten.bp.format.FormatStyle

//todo move to a different package
class LocalTimeConverters {

    @TypeConverters
    fun fromStringToLocalTime(str: String): LocalTime =
        LocalTime.parse(str, DateTimeFormatter.ofLocalizedTime(FormatStyle.FULL))


    @TypeConverters
    fun fromLocalTimeToString(localTime: LocalTime): String {
        val formatter = DateTimeFormatter.ofLocalizedTime(FormatStyle.SHORT)
        return localTime.format(formatter)
    }
}