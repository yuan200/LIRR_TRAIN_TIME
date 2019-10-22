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
import java.util.*
import kotlin.collections.HashMap

class LirrViewModel(application: Application, private val lirrGtfsDao: LirrGtfsDao) :
    AndroidViewModel(application) {

    private val lirrGtfsBase: LirrGtfsBase by lazy {
        LirrGtfsBase.getInstance(application) ?: throw IllegalStateException()
    }

    val revised: MutableLiveData<Date> by lazy {
        MutableLiveData<Date>().also {
            //todo runBlocking probably is not a good idea
            runBlocking {
                it.value = lirrGtfsDao.getRevised()

            }
        }
    }

    //todo change to private
    /**
     * todo for Lirr station just return the nearest one station
     */
    val nearByStops: MutableLiveData<Stop> by lazy {
        MutableLiveData<Stop>().also {
            //todo viewModelSclpe ?
            runBlocking {
                val allStops = lirrGtfsBase.stopDao().allStops()
                Timber.i("before filter size ${allStops.size}")
                val stopDistanceMap = HashMap<Stop, Double>().also { h ->
                    allStops.forEach {
                        val distance = getDistance(
                            it.stopLat.toDouble(),
                            it.stopLon.toDouble(),
                            40.744538,
                            -73.644054
                        )
                        Timber.i("${it.stopDesc} distance is $distance")
                        h[it] = distance
                    }
                }


                Timber.i("after filter size ${stopDistanceMap.size}")
                val sortStops = stopDistanceMap.toList().sortedBy {
                    (_, value) -> value
                }
                //todo this is ugly might have bugs
                if (sortStops.isEmpty())
                    it.value = Stop.empty()
                else
                    it.value = sortStops[0].first
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
