package com.yuan.nyctransit.features.lirr

import retrofit2.http.GET
import retrofit2.Call

interface LirrGtfsApi {
    companion object {
        val link = "http://web.mta.info"
    }

    @GET("developers/data/lirr/lirr_gtfs.json")
    fun getLirrGtfs(): Call<LirrGtfs>

}