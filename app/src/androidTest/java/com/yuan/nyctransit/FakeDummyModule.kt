package com.yuan.nyctransit

import com.yuan.nyctransit.core.di.DummyClass
import dagger.Module
import dagger.Provides

@Module
class FakeDummyModule {

    @Provides
    fun provideFakeDummy(): DummyClass = FakeDummyClass("I am a fake dummy class")
}