package com.yuan.nyctransit.features.lirr

import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers

interface LirrFeedApi {
    @GET("Dataservice/mtagtfsfeeds/lirr%2Fgtfs-lirr")
    @Headers("x-api-key: ktS7KEh17C4bAd9XKEfJV7Z3s1Fw0poy5QLxnIYN")
    fun lirrFeed(): Call<ResponseBody>
}