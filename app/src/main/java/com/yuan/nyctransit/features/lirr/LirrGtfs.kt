package com.yuan.nyctransit.features.lirr

import android.content.Context
import androidx.room.*
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import com.yuan.nyctransit.core.database.*
import timber.log.Timber

@Entity(tableName = "gtfs_overview")
@JsonClass(generateAdapter = true)
data class LirrGtfs(
    @ColumnInfo("agency_id") @Json(name = "agency_id") var agencyId: String,
    @ColumnInfo("feed_version") @Json(name = "feed_version") var feedVersion: String,
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
    @Json(name = "feed_info") var feedInfo: FeedInfo,
    var agency: Agency,
    var stops: List<Stop>,
    var routes: List<Route>,
    var shapes: List<Shape>,
    var trips: List<Trip>,
    var stop_times: List<StopTime>,
    var calendar_dates: List<CalendarDate>
)
