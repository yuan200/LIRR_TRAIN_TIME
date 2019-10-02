package com.yuan.nyctransit.features.lirr

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.yuan.nyctransit.core.database.LirrGtfsBase
import com.yuan.nyctransit.core.database.LirrGtfsDao

class LirrViewModelFactory(private val context: Context) :
ViewModelProvider.NewInstanceFactory(){

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        val dao = LirrGtfsBase.getInstance(context)!!.LirrGtfsDao()
        return LirrViewModel(dao) as T
    }
}