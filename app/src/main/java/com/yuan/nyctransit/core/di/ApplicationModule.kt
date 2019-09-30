package com.yuan.nyctransit.core.di

import android.content.Context
import com.yuan.nyctransit.AndroidApplication
import com.yuan.nyctransit.BuildConfig
import com.yuan.nyctransit.features.lirr.LirrFeedRepository
import com.yuan.nyctransit.features.lirr.LirrGtfs
import com.yuan.nyctransit.features.lirr.LirrGtfsApi
import com.yuan.nyctransit.features.lirr.LirrGtfsRepository
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named
import javax.inject.Singleton

@Module
class ApplicationModule(private val application: AndroidApplication) {

    @Provides @Singleton fun provideApplicationContext(): Context = application

    @Provides @Singleton fun provideLirrRepository(dataSource: LirrFeedRepository.Network): LirrFeedRepository = dataSource

    @Provides @Named("apiMta") @Singleton fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://api-endpoint.mta.info")
            .client(createClient())
            .build()
    }

    @Provides @Singleton fun provideLirrGtfsRepository(dataSource: LirrGtfsRepository.Network)
    : LirrGtfsRepository = dataSource

    @Provides @Named("webMta") @Singleton fun provideLirrGtfsRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(LirrGtfsApi.link)
            .addConverterFactory(GsonConverterFactory.create())
            .client(createClient())
            .build()
    }

    private fun createClient(): OkHttpClient {
        val okHttpClientBuilder: OkHttpClient.Builder = OkHttpClient.Builder()
        if (BuildConfig.DEBUG) {
        }
        return okHttpClientBuilder.build()
    }

}