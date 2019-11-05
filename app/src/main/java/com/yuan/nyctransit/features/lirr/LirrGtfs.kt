package com.yuan.nyctransit.features.lirr

import android.content.Context
import androidx.room.*
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import com.yuan.nyctransit.core.database.*
import timber.log.Timber
import java.util.*

@Entity(tableName = "gtfs_overview", primaryKeys = ["agency_id", "feed_version"])
@JsonClass(generateAdapter = true)
data class LirrGtfs(
    @ColumnInfo("agency_id") @Json(name = "agency_id") var agencyId: String,
    @ColumnInfo("feed_version") @Json(name = "feed_version") var feedVersion: String,
    var revised: Date,
    @Ignore var gtfs: Gtfs? //todo is nullable ok here
) {
    //todo the constructor here is because it give error when it has @Ignore about
    constructor(): this("", "", Date(), null)
    companion object {
        fun empty(): LirrGtfs = LirrGtfs("", "",Date(),null)

    }


}
fun LirrGtfs.saveToDB(context: Context) {
    val db = LirrGtfsBase.getInstance(context)
    Timber.i("saving into database")
    db!!.lirrGtfsDao().insert(this)
}
@JsonClass(generateAdapter = true)
data class Gtfs(
    @Json(name = "feed_info") var feedInfo: FeedInfo,
    var agency: Agency,
    var stops: List<Stop>,
    var routes: List<Route>,
    var shapes: List<Shape>,
    var trips: List<Trip>,
    var stop_times: List<StopTime>,
    var calendar_dates: List<CalendarDate>
)
