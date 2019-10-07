package com.yuan.nyctransit.features.lirr

import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.yuan.nyctransit.AndroidApplication
import com.yuan.nyctransit.core.database.LirrGtfsBase
import com.yuan.nyctransit.core.database.LirrGtfsDao
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking
import timber.log.Timber

class LirrViewModel(private val lirrGtfsDao: LirrGtfsDao,
                    val application: AndroidApplication) : AndroidViewModel(application) {

    private val lirrGtfsBase: LirrGtfsBase by lazy {
        LirrGtfsBase.getInstance(application)?: throw IllegalStateException()
    }

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