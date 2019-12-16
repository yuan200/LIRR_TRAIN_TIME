package com.yuan.nyctransit.core.di

import com.yuan.nyctransit.ui.dashboard.NearbyFragment
import com.yuan.nyctransit.ui.home.HomeFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class FragmentInjector {
    @ContributesAndroidInjector
    abstract fun contributeHomeFragmentAndroidInjector(): HomeFragment

    @ContributesAndroidInjector
    abstract fun constributeNearbyFragmetnAndroidInjector(): NearbyFragment
}