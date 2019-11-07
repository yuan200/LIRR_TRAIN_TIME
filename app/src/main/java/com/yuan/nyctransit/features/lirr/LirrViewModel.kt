package com.yuan.nyctransit.features.lirr

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.yuan.nyctransit.core.database.LirrGtfsBase
import com.yuan.nyctransit.core.database.LirrGtfsDao
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking

class LirrViewModel(application: Application, private val lirrGtfsDao: LirrGtfsDao) :
    AndroidViewModel(application) {

    private val lirrGtfsBase: LirrGtfsBase by lazy {
        LirrGtfsBase.getInstance(application) ?: throw IllegalStateException()
    }

    val revised: MutableLiveData<String> by lazy {
        MutableLiveData<String>().also {
            //todo runBlocking probably is not a good idea
            runBlocking {
                it.value = lirrGtfsDao.getRevised()

            }
        }
    }

//    fun getLirrGtfsRevised() = viewModelScope.launch {
//        revised.value = lirrGtfsDao.getRevised()
//        Timber.i("revised value: ${revised.value}")
//    }

    //todo change to viewmodel scope
    fun getLirrGtfsRevised() = CoroutineScope(Dispatchers.Default).async {
        revised.value = lirrGtfsDao.getRevised()
    }

}
