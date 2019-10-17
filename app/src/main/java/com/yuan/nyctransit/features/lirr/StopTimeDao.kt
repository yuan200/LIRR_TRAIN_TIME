package com.yuan.nyctransit.features.lirr

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import com.yuan.nyctransit.core.database.StopTime

@Dao
interface StopTimeDao {

    @Insert(onConflict = REPLACE)
    fun insert(stopTime: StopTime)

    @Query("SELECT * FROM stop_time where trip_id = :tripId")
    fun getByTripId(tripId: String): StopTime

    @Query("SELECT * FROM stop_time where trip_id = :tripId AND stop_id = :stopId")
    suspend fun getArrivalTime(tripId: String, stopId: String):StopTime
}