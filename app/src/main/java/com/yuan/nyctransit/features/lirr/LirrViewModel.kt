package com.yuan.nyctransit.features.lirr

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.yuan.nyctransit.core.database.LirrGtfsBase
import com.yuan.nyctransit.core.database.LirrGtfsDao
import com.yuan.nyctransit.core.database.Stop
import com.yuan.nyctransit.utils.getDistance
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking
import timber.log.Timber

class LirrViewModel(application: Application, private val lirrGtfsDao: LirrGtfsDao) : AndroidViewModel(application) {

    private val lirrGtfsBase: LirrGtfsBase by lazy {
        LirrGtfsBase.getInstance(application)?: throw IllegalStateException()
    }

    val revised: MutableLiveData<String> by lazy {
        MutableLiveData<String>().also {
            //todo runBlocking probably is not a good idea
            runBlocking {
                it.value = lirrGtfsDao.getRevised()

            }
        }
    }

    //todo change to private
    val nearByStops: MutableLiveData<List<Stop>> by lazy {
        MutableLiveData<List<Stop>>().also {
            //todo viewModelSclpe ?
            runBlocking {
                val allStops = lirrGtfsBase.stopDao().allStops()
                Timber.i("before filter size ${allStops.size}")
                val nearbyStops = allStops.filter filter@{
                    val distance = getDistance(
                        it.stopLat.toDouble(),
                        it.stopLon.toDouble(),
                        40.744538,
                        -73.644054
                    )
                    Timber.i("${it.stopDesc} distance is $distance")
                    if (distance < 3)
                        return@filter true
                    return@filter false
                }.toList()
                Timber.i("after filter size ${nearbyStops.size}")
                it.value = nearbyStops


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
