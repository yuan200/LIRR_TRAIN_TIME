package com.yuan.nyctransit.core.database

import org.junit.Assert
import org.junit.Test
import java.text.SimpleDateFormat

class DateConvertersTest {
    
    val converter = DateConverters()

    @Test
    fun fromStringToDate() {
        val dateStr = "2019:10:22"
        val simpleDateFormat = SimpleDateFormat("HH:mm:ss")
        val acutalDate = simpleDateFormat.parse(dateStr)
        Assert.assertEquals(converter.fromStringToDate(dateStr), acutalDate)

    }

}