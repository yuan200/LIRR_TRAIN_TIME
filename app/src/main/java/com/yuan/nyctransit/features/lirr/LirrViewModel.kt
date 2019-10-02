package com.yuan.nyctransit.features.lirr

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yuan.nyctransit.core.database.LirrGtfsDao
import kotlinx.coroutines.launch

class LirrViewModel(private val lirrGtfsDao: LirrGtfsDao) : ViewModel() {
    fun getLirrGtfsRevised() = viewModelScope.launch {
        lirrGtfsDao.getRevised()
    }
}