package com.yuan.nyctransit.features.lirr

import retrofit2.Call
import retrofit2.Retrofit
import javax.inject.Inject
import javax.inject.Named
import javax.inject.Singleton

@Singleton
class LirrGtfsService
@Inject constructor(@Named("webMta") retrofit: Retrofit) : LirrGtfsApi{
    private val lirrApi by lazy { retrofit.create(LirrGtfsApi::class.java) }

    override fun getLirrGtfs(): Call<LirrGtfs> = lirrApi.getLirrGtfs()
}