package com.yuan.nyctransit.features.lirr

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yuan.nyctransit.core.database.LirrGtfsDao
import kotlinx.coroutines.*
import timber.log.Timber

class LirrViewModel(private val lirrGtfsDao: LirrGtfsDao) : ViewModel() {

    val revised: MutableLiveData<String> by lazy {
        MutableLiveData<String>().also {
            runBlocking {
                it.value = lirrGtfsDao.getRevised()

            }
            Timber.i("before join")
            Timber.i("after join")
        }
    }

//    fun getLirrGtfsRevised() = viewModelScope.launch {
//        revised.value = lirrGtfsDao.getRevised()
//        Timber.i("revised value: ${revised.value}")
//    }

    fun getLirrGtfsRevised() = CoroutineScope(Dispatchers.Default).async {
        revised.value = lirrGtfsDao.getRevised()
        Timber.i("revised value: ${revised.value}")
    }
}