package com.yuan.nyctransit.features.lirr

import android.content.Context
import androidx.room.*
import com.google.gson.annotations.SerializedName
import com.yuan.nyctransit.core.database.*
import timber.log.Timber
import java.util.*

@Entity(tableName = "gtfs_overview")
data class LirrGtfs(
    @ColumnInfo("agency_id") @SerializedName("agency_id") var agencyId: String,
    @ColumnInfo("feed_version") @SerializedName("feed_version") var feedVersion: String,
    @PrimaryKey var revised: String,
    @Ignore var gtfs: Gtfs? //todo is nullable ok here
) {
    //todo the constructor here is because it give error when it has @Ignore about
    constructor(): this("", "", "", null)
    companion object {
        fun empty(): LirrGtfs = LirrGtfs("", "","",null)

    }


}
fun LirrGtfs.saveToDB(context: Context) {
    val db = LirrGtfsBase.getInstance(context)
    Timber.i("saving into database")
    db!!.lirrGtfsDao().insert(this)
    val result = db.lirrGtfsDao().getAll()
    result.toString()
}
data class Gtfs(
    @SerializedName("feed_info") var feedInfo: FeedInfo,
    var agency: Agency,
    var stops: List<Stop>,
    var routes: List<Route>,
    var shapes: List<Shape>,
    var trips: List<Trip>,
    @SerializedName("stop_times") var stopTimes: List<StopTime>,
    @SerializedName("calendar_dates") var calendarDates: List<CalendarDate>
)
