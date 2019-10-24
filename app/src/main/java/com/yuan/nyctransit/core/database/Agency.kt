package com.yuan.nyctransit.core.database

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Agency(
    @Json(name = "agency_id") var agencyId: String,
    @Json(name = "agency_name") var agencyName: String,
    @Json(name = "agency_url") var agencyUrl: String,
    @Json(name = "agency_timezone") var agencyTimeZone: String,
    @Json(name = "agency_lang") var agencyLang: String,
    @Json(name = "agency_phone") var agencyPhone: String
)
