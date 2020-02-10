package com.yuan.nyctransit.core.di

import com.yuan.nyctransit.AndroidApplication
import dagger.Component
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(
    modules = [ApplicationModule::class,
        AndroidSupportInjectionModule::class,
        PermissionsModule::class,
        ActivityInjector::class,
        FragmentInjector::class,
        DummyModule::class,
        ViewModelModule::class]
)
interface ApplicationComponent {

    fun inject(application: AndroidApplication)
}