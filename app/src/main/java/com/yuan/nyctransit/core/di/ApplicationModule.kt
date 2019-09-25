package com.yuan.nyctransit.core.di

import android.content.Context
import com.yuan.nyctransit.AndroidApplication
import com.yuan.nyctransit.BuildConfig
import com.yuan.nyctransit.features.lirr.LirrFeedRepository
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
class ApplicationModule(private val application: AndroidApplication) {

    @Provides @Singleton fun provideApplicationContext(): Context = application

    @Provides @Singleton fun provideLirrRepository(dataSource: LirrFeedRepository.Network): LirrFeedRepository = dataSource

    @Provides @Singleton fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://api-endpoint.mta.info")
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