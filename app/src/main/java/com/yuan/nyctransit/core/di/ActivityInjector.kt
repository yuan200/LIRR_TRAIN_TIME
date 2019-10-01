package com.yuan.nyctransit.core.di

import com.yuan.nyctransit.MainActivity
import com.yuan.nyctransit.platform.PermissionsActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityInjector {

    @ContributesAndroidInjector
    abstract fun contributeMainActivityAndroidInjector(): MainActivity

    @ContributesAndroidInjector
    abstract fun contributePermissionsActivity(): PermissionsActivity
}