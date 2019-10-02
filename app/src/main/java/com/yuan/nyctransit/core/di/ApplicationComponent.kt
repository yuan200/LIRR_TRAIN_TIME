package com.yuan.nyctransit.core.di

import com.yuan.nyctransit.AndroidApplication
import com.yuan.nyctransit.MainActivity
import com.yuan.nyctransit.platform.PermissionsActivity
import com.yuan.nyctransit.ui.home.HomeFragment
import dagger.Component
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(
    modules = [ApplicationModule::class,
        AndroidSupportInjectionModule::class,
        PermissionsModule::class,
        ActivityInjector::class,
        FragmentInjector::class]
)
interface ApplicationComponent {

    fun inject(application: AndroidApplication)
}