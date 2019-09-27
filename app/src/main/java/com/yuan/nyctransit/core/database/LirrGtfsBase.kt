package com.yuan.nyctransit.core.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = arrayOf(LirrGtfsTable::class), version = 1)
abstract class LirrGtfsBase : RoomDatabase() {
    abstract fun LirrGtfsDao(): LirrGtfsDao

    companion object {
        private var INSTANCE: LirrGtfsBase? = null

        fun getInstance(context: Context): LirrGtfsBase? {
            if (INSTANCE == null) {
                synchronized(LirrGtfsBase::class) {
                    if (INSTANCE == null) {
                        INSTANCE = Room.databaseBuilder(
                            context.applicationContext,
                            LirrGtfsBase::class.java,
                            "LirrGtfsTable.db")
                            .build()
                    }
                }
            }
            return INSTANCE
        }

        fun destroyInstance() {
            INSTANCE = null
        }
    }
}