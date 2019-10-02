package com.yuan.nyctransit.core.database

import com.google.gson.annotations.SerializedName

data class Calendardate(
    @SerializedName("service_id") var serviceId: String,
    var date: Int,
    @SerializedName("exception_type") var exceptionType: String
) {
}