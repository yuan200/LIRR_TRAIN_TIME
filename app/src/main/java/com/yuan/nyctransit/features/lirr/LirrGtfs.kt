package com.yuan.nyctransit.features.lirr

import androidx.room.ColumnInfo
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import com.yuan.nyctransit.core.database.*

data class LirrGtfs(
    @SerializedName("agency_id") var agencyId: String,
    @SerializedName("feed_version") var feedVersion: String,
    var revised: String,
    var gtfs: Gtfs? //todo is nullable ok here
) {
    companion object {
        fun empty(): LirrGtfs = LirrGtfs("", "","",null)
    }
}

data class Gtfs(
    @SerializedName("feed_info") var feedInfo: FeedInfo,
    var agency: Agency,
    var stops: List<Stop>,
    var routes: List<Route>,
    var shapes: List<Shape>,
    var trips: List<Trip>,
    @SerializedName("stop_times") var stopTimes: List<StopTime>,
    @SerializedName("calendar_dates") var calendarDates: List<Calendardate>
)
