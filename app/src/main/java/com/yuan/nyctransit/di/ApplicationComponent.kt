package com.yuan.nyctransit.di

import com.yuan.nyctransit.AndroidApplication
import com.yuan.nyctransit.MainActivity
import com.yuan.nyctransit.platform.PermissionsActivity
import dagger.Component
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(
    modules = [ApplicationModule::class, ApplicationModule::class,
        AndroidSupportInjectionModule::class,
        PermissionsModule::class,
        ActivityInjector::class]
)
interface ApplicationComponent {

    fun inject(application: AndroidApplication)

    fun inject(mainActivity: MainActivity)

    fun inject(activity: PermissionsActivity)
}