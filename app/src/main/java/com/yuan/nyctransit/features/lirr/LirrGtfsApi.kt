package com.yuan.nyctransit.features.lirr

import retrofit2.http.GET
import retrofit2.Call

interface LirrGtfsApi {
    companion object {
        //todo http is using clearText, i add android:usesCleartextTraffic="true"
        // to Manifest file for right now, should find a better solution
        val link = "http://web.mta.info"
    }

    @GET("developers/data/lirr/lirr_gtfs.json")
    fun getLirrGtfs(): Call<LirrGtfs>

}