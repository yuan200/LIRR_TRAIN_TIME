package com.yuan.nyctransit.core.database

import org.junit.Test
import java.util.*

class DateTimeConvertersTest {
    //todo no assertion

    val converter = DateTimeConverters()
    @Test
    fun fromStringToDataTime() {
        val dateStr = "2019-10-18T13:57:13-0400"
        val dateTime = converter.fromStringToDataTime(dateStr)
        println(dateTime)
    }

    @Test
    fun fromDateTimeToString() {
        val now = Date()
        val nowStr = converter.fromDateTimeToString(now)
        println(nowStr)
    }
}