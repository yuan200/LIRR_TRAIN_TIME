package com.yuan.nyctransit.utils

import kotlin.math.*

/**
 * @return return shortest distance between two coordinates in meter
 */
fun getDistance(lat1: Double, lon1: Double, lat2: Double, lon2: Double): Double {
    val averageRadiusOfEarthM = 6371000

    val latDistance = Math.toRadians(lat1 - lat2)
    val lngDistance = Math.toRadians(lon1 - lon2)

    val a = sin(latDistance / 2) * sin(latDistance / 2) + (cos(Math.toRadians(lat1)) * cos(
        Math.toRadians(lat2)
    ) * sin(lngDistance / 2) * sin(lngDistance / 2))

    val c = 2 * atan2(sqrt(a), sqrt(1 - a))

    return round(averageRadiusOfEarthM * c)
}