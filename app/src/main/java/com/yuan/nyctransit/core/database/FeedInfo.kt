package com.yuan.nyctransit.core.database

import com.squareup.moshi.Json

data class FeedInfo(
    @Json(name = "feed_publisher_name") var feedPublisherName: String,
    @Json(name = "feed_publisher_url") var feedPublisherUrl: String,
    @Json(name = "feed_timezone") var feedTimezone: String,
    @Json(name = "feed_lang") var feedLang: String,
    @Json(name = "feed_version") var feedVersion: String
) {
}