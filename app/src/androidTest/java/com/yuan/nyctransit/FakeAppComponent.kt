package com.yuan.nyctransit

import com.yuan.nyctransit.core.di.ApplicationComponent
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        FakeDummyModule::class
    ]
)
interface FakeAppComponent : ApplicationComponent {
}