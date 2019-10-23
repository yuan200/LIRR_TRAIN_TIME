package com.yuan.nyctransit.core.database

import org.junit.Test

class LocalTimeConvertersTest {

    val convert = LocalTimeConverters()

    @Test
    fun fromStringToLocalTime() {
        val timeStr = "22:22:22"
        val time = convert.fromStringToLocalTime(timeStr)
        println(time)
    }

    @Test
    fun fromLocalTimeToString() {
    }
}