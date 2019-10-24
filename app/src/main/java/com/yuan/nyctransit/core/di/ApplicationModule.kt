package com.yuan.nyctransit.core.di

import android.app.Application
import android.content.Context
import com.google.gson.GsonBuilder
import com.yuan.nyctransit.AndroidApplication
import com.yuan.nyctransit.BuildConfig
import com.yuan.nyctransit.core.database.LirrGtfsBase
import com.yuan.nyctransit.features.lirr.*
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

    //todo this is duplicate with application context provide method
    @Provides @Singleton fun provideApplication(): Application = application

    @Provides @Singleton fun provideLirrRepository(dataSource: NetworkLirrRepository): LirrFeedRepository = dataSource

    @Provides @Named("apiMta") @Singleton fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://api-endpoint.mta.info")
            .client(createClient())
            .build()
    }

    @Provides @Singleton fun provideLirrGtfsRepository(dataSource: LirrGtfsNetworkRepository)
    : LirrGtfsRepository = dataSource

    @Provides @Named("webMta") @Singleton fun provideLirrGtfsRetrofit(): Retrofit {
        val gson = GsonBuilder()
//            .setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
            .setDateFormat("HH:mm:ss")
            .create()
        return Retrofit.Builder()
            .baseUrl(LirrGtfsApi.link)
            .addConverterFactory(GsonConverterFactory.create())
            .client(createClient())
            .build()
    }

    @Provides @Singleton fun provideLirrGtfsBase(lirrGtfsBase: LirrGtfsBase) = LirrGtfsBase.getInstance(application)

    private fun createClient(): OkHttpClient {
        val okHttpClientBuilder: OkHttpClient.Builder = OkHttpClient.Builder()
        if (BuildConfig.DEBUG) {
        }
        return okHttpClientBuilder.build()
    }

}