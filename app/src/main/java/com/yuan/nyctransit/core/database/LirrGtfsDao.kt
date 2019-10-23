package com.yuan.nyctransit.core.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import com.yuan.nyctransit.features.lirr.LirrGtfs

@Dao
interface LirrGtfsDao {

    @Query("SELECT * FROM gtfs_overview")
    fun getAll(): List<LirrGtfs>

    @Query("SELECT revised FROM gtfs_overview WHERE revised IS NOT NULL LIMIT 1")
    suspend fun getRevised(): String

    @Insert(onConflict = REPLACE)
    fun insert(lirrGtfs: LirrGtfs)


    @Query("DELETE from gtfs_overview")
    fun deleteAll()

}