package com.yuan.nyctransit.core.di

import dagger.Module
import dagger.Provides

@Module
class DummyModule {
    @Provides
    fun provideDummyObject(): DummyClass = DummyClass("I am a dummy object")
}