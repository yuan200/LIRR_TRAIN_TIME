package com.yuan.nyctransit.features.lirr

data class StopTimeUpdateView(
    val stopName: String,
    val serviceLine: String,
    val destination: String,
    val arrivingTime: String,
    val delay: Int,
    val tripHeadSign: String = ""
)
