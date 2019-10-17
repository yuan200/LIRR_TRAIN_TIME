package com.yuan.nyctransit.core.di

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import javax.inject.Inject
import javax.inject.Provider
import javax.inject.Singleton

//todo this doesn't belong to di package
@Singleton
class NearbyViewModelFactory
//todo Provider<AndroidViewModel> should be Provider<ViewModel>
@Inject constructor(
    application: Application,
    private val viewModels: MutableMap<Class<out ViewModel>, Provider<AndroidViewModel>>
) :
    ViewModelProvider.AndroidViewModelFactory(application) {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T = viewModels[modelClass]?.get() as T
}