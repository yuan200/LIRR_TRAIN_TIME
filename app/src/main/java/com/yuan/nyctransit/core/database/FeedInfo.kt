package com.yuan.nyctransit.core.database

data class FeedInfo(var feedPublisherName : String,
                    var feedPublisherUrl : String,
                    var feedTimezone : String,
                    var feedLang : String,
                    var feedVersion : String) {
}