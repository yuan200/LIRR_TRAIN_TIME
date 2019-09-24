package com.yuan.nyctransit.core.di

import android.content.Context
import com.yuan.nyctransit.AndroidApplication
import com.yuan.nyctransit.features.lirr.LirrFeedRepository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class ApplicationModule(private val application: AndroidApplication) {

    @Provides @Singleton fun provideApplicationContext(): Context = application

    @Provides @Singleton fun provideLirrRepository(dataSource: LirrFeedRepository.Network): LirrFeedRepository = dataSource

}