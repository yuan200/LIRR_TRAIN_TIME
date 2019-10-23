package com.yuan.nyctransit.core.database

import androidx.room.TypeConverters
import org.threeten.bp.LocalTime
import org.threeten.bp.format.DateTimeFormatter
import org.threeten.bp.format.FormatStyle

//todo move to a different package
class LoalTimeConverters {

    @TypeConverters
    fun fromStringToDateTime(str: String) {
        val formatter = LocalTime.parse(str, DateTimeFormatter.ofLocalizedTime(FormatStyle.SHORT))
    }
}