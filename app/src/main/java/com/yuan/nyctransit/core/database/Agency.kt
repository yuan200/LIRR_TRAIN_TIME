package com.yuan.nyctransit.core.database

import com.google.gson.annotations.SerializedName

data class Agency(
    @SerializedName("agency_id") var agencyId: String,
    @SerializedName("agency_name") var agencyName: String,
    @SerializedName("agency_url") var agencyUrl: String,
    @SerializedName("agency_timezone") var agencyTimeZone: String,
    @SerializedName("agency_lang") var agencyLang: String,
    @SerializedName("agency_phone") var agencyPhone: String
)
