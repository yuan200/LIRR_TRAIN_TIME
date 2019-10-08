package com.yuan.nyctransit.features.lirr

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.yuan.nyctransit.core.database.LirrGtfsBase

class LirrViewModelFactory(private val application: Application) :
ViewModelProvider.AndroidViewModelFactory(application){

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        val dao = LirrGtfsBase.getInstance(application)!!.lirrGtfsDao()
        return LirrViewModel(application, dao) as T
    }
}

