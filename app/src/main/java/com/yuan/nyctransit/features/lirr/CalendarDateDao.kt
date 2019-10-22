package com.yuan.nyctransit.features.lirr

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE

@Dao
interface CalendarDateDao {

    @Insert(onConflict = REPLACE)
    fun insert(calendarDate: CalendarDate)
}