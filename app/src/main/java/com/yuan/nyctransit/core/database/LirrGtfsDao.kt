package com.yuan.nyctransit.core.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import com.yuan.nyctransit.features.lirr.LirrGtfs

@Dao
interface LirrGtfsDao {

    @Query("SELECT * from gtfs_overview")
    fun getAll(): List<LirrGtfs>

    @Insert(onConflict = REPLACE)
    fun insert(lirrGtfs: LirrGtfs)

    @Query("DELETE from gtfs_overview")
    fun deleteAll()
}