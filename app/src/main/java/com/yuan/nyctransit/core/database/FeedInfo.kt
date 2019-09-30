package com.yuan.nyctransit.core.database

import com.google.gson.annotations.SerializedName

data class FeedInfo(
    @SerializedName("feed_publisher_name") var feedPublisherName: String,
    @SerializedName("feed_publisher_url") var feedPublisherUrl: String,
    @SerializedName("feed_timezone") var feedTimezone: String,
    @SerializedName("feed_lang") var feedLang: String,
    @SerializedName("feed_version") var feedVersion: String
) {
}