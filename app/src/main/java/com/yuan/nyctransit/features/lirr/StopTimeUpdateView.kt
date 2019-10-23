package com.yuan.nyctransit.features.lirr

import java.util.*

data class StopTimeUpdateView(
    val stopName: String,
    val serviceLine: String,
    val destination: String,
    val arrivingTime: Date,
    val delay: Int,
    val tripHeadSign: String = ""
)
