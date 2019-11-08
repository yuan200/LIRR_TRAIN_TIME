package com.yuan.nyctransit.features.lirr

import org.threeten.bp.LocalDate
import org.threeten.bp.LocalDateTime

data class StopTimeUpdateView(
    val stopName: String,
    val serviceLine: String,
    val destination: String,
    var arrivingTimeStr: String,
    var arrivingTime: LocalDateTime?,
    var delay: Int = 0,
    val tripHeadSign: String = "",
    val routeColor: String = "FFFFFF"
){
    init {
        computeArrivingTime()
    }

    companion object {
        fun placeHolder(): StopTimeUpdateView =
            StopTimeUpdateView(
                "",
                "",
                "",
                "00:00:00",
                null,
                0,
                "",
                "FFFFFF"
            )
    }

    private fun computeArrivingTime() {
        if (arrivingTimeStr.isEmpty()) throw IllegalStateException("time can not be empty string")
        val timeList =arrivingTimeStr.split(":")
        var hour = timeList[0].toInt()
        val minute = timeList[1].toInt()

        if (hour < 24) {
            arrivingTime = LocalDate.now().atTime(hour, minute)
        } else {
            hour -= 24
            arrivingTime = LocalDate.now().plusDays(1).atTime(hour, minute)
        }
    }

}
