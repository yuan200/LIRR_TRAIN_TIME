package com.yuan.nyctransit.features.lirr

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import com.yuan.nyctransit.core.database.Stop

@Dao
interface StopDao {

    @Insert(onConflict = REPLACE)
    fun insert(stop: Stop)

    @Query("SELECT * FROM STOP")
    fun getAll(): List<Stop>

    @Query("SELECT stop_name FROM STOP")
    suspend fun getAllStopName(): List<String>

    @Query("SELECT * FROM stop")
    suspend fun allStops(): List<Stop>

    @Query("SELECT * FROM STOP WHERE STOP_ID = :stopId")
    suspend fun getStop(stopId: String): Stop
}