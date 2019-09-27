package com.yuan.nyctransit.core.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "lirrGtfs")
data class LirrGtfsTable(
    @PrimaryKey(autoGenerate = true) var id: Long?,
    @ColumnInfo(name = "agency_id") var agencyId: String,
    @ColumnInfo(name = "feed_version") var feedVersion: String,
    @ColumnInfo(name = "revised") var revised: String
)

//data class Gtfs(
//    @ColumnInfo(name = "feed_info") var feedInfo: FeedInfo,
//    @ColumnInfo(name = "agency") var agency: Agency,
//    @ColumnInfo(name = "stops") var stops: List<Stop>,
//    @ColumnInfo(name = "routes") var routes: List<Route>,
//    @ColumnInfo(name = "shapes") var shapes: List<Shape>,
//    @ColumnInfo(name = "trips") var trips: List<Trip>,
//    @ColumnInfo(name = "stop_times") var stopTimes: List<StopTime>,
//    @ColumnInfo(name = "calendar_dates") var calendarDates: List<Calendardate>
//)