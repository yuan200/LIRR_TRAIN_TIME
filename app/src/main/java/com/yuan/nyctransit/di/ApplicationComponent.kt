package com.yuan.nyctransit.di

import com.yuan.nyctransit.AndroidApplication
import dagger.Component
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(
    modules = [ApplicationModule::class, ApplicationModule::class,
        AndroidSupportInjectionModule::class,
        ActivityInjector::class]
)
interface ApplicationComponent {

    fun inject(application: AndroidApplication)
}