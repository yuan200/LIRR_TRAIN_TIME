package com.yuan.nyctransit.core.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.yuan.nyctransit.features.lirr.*

//todo checkout room export schema
@Database(
    entities = arrayOf(
        LirrGtfs::class,
        Stop::class,
        Trip::class,
        StopTime::class,
        CalendarDate::class
    ), version = 2
)
@TypeConverters(LocalTimeConverters::class)
abstract class LirrGtfsBase : RoomDatabase() {

    abstract fun lirrGtfsDao(): LirrGtfsDao

    abstract fun stopDao(): StopDao

    abstract fun tripDao(): TripDao

    abstract fun stopTimeDao(): StopTimeDao

    abstract fun calendarDateDao(): CalendarDateDao

    companion object {
        private var INSTANCE: LirrGtfsBase? = null

        //todo can we use kotlin singleton object here
        fun getInstance(context: Context): LirrGtfsBase? {
            if (INSTANCE == null) {
                synchronized(LirrGtfsBase::class) {
                    if (INSTANCE == null) {
                        INSTANCE = Room.databaseBuilder(
                            context.applicationContext,
                            LirrGtfsBase::class.java,
                            "LirrGtfsTable.db"
                        )
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