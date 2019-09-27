package com.yuan.nyctransit.core.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query

@Dao
interface LirrGtfsDao {

    @Query("SELECT * from lirrGtfs")
    fun getAll(): List<LirrGtfsTable>

    @Insert(onConflict = REPLACE)
    fun insert(lirrGtfs: LirrGtfsTable)

    @Query("DELETE from lirrGtfs")
    fun deleteAll()
}