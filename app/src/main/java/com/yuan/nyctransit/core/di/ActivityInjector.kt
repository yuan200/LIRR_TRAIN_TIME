package com.yuan.nyctransit.core.di

import com.yuan.nyctransit.MapsActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityInjector {
    @ContributesAndroidInjector
    abstract fun contributeMapsActivityAndroidInjector(): MapsActivity
}