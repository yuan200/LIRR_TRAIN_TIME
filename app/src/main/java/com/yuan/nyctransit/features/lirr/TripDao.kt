package com.yuan.nyctransit.features.lirr

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import com.yuan.nyctransit.core.database.Trip

@Dao
interface TripDao {

    @Insert(onConflict = REPLACE)
    fun insert(trip: Trip)

    @Query("SELECT * FROM trip WHERE trip_id = :tripId")
    fun getByTripId(tripId: String): Trip
}