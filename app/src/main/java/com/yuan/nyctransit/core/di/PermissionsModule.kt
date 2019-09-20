package com.yuan.nyctransit.core.di

import android.app.Application
import android.content.Context
import com.yuan.nyctransit.Permissions.PermissionManagerImpl
import com.yuan.nyctransit.Permissions.PermissionsManager
import dagger.Module
import dagger.Provides
import io.reactivex.android.schedulers.AndroidSchedulers
import javax.inject.Singleton

@Module
class PermissionsModule {
    @Provides
    @Singleton
    fun providePermissionsManager(context: Context): PermissionsManager =
        PermissionManagerImpl(context as Application, AndroidSchedulers.mainThread())
}