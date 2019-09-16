package com.yuan.nyctransit.di

import android.content.Context
import com.yuan.nyctransit.AndroidApplication
import dagger.Component
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class ApplicationModule(private val application: AndroidApplication) {

    @Provides @Singleton fun provideApplicationContext(): Context = application

}