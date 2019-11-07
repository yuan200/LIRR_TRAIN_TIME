package com.yuan.nyctransit.ui.dashboard

import android.app.Application
import android.location.Location
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.yuan.nyctransit.core.database.LirrGtfsBase
import com.yuan.nyctransit.core.database.Stop
import com.yuan.nyctransit.features.lirr.GetLirrFeed
import com.yuan.nyctransit.features.lirr.StopTimeUpdateView
import com.yuan.nyctransit.utils.getDistance
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import javax.inject.Inject

class NearbyViewModel
@Inject constructor(application: Application, val lirrFeed: GetLirrFeed) :
    AndroidViewModel(application) {

    private val lirrGtfsBase: LirrGtfsBase by lazy {
        LirrGtfsBase.getInstance(application) ?: throw IllegalStateException()
    }

    private val _text = MutableLiveData<String>().apply {
        value = "This is dashboard Fragment"
    }
    val text: LiveData<String> = _text

    val currentLocation = MutableLiveData<Location>().apply {
        value = Location("empty").apply {
            latitude = 0.0
            longitude = 0.0
        }
    }

    private val _feed: MutableLiveData<MutableList<StopTimeUpdateView>> by lazy {

        MutableLiveData<MutableList<StopTimeUpdateView>>().apply {
            //todo hardcode
            lirrFeed.stopId = nearByStops().stopId
            //todo viewmodeScope is use main thread, how to avoid useing CoroutineScope(Dispatchers.IO)
            lirrFeed(CoroutineScope(Dispatchers.IO), true) {
                if (it.isRight) value =
                    it.either(
                        {},
                        { stopTimeUpdateViewList -> stopTimeUpdateViewList }) as MutableList<StopTimeUpdateView>
            }
        }

    }

    fun getFeed() = _feed

    //todo change to private
    //todo change it to fun?
    //todo maybe lazy is bad here
    /**
     * todo for Lirr station just return the nearest one station
     */
    private fun nearByStops(): Stop {
        //todo viewModelSclpe ?
        var stops: List<Stop> = emptyList()
        val allStops = CoroutineScope(Dispatchers.IO).async { lirrGtfsBase.stopDao().allStops() }
        var stopsJob = CoroutineScope(Dispatchers.IO).launch {
            stops = allStops.await()
        }

        //todo change this
        Thread.sleep(3000)

        val stopDistanceMap = HashMap<Stop, Double>().also { h ->
            stops.forEach {
                val distance = getDistance(
                    it.stopLat.toDouble(),
                    it.stopLon.toDouble(),
//                    40.744538,
//                    -73.644054
                    currentLocation.value!!.latitude,
                    currentLocation.value!!.longitude

                )
                h[it] = distance
            }
        }

        val sortStops = stopDistanceMap.toList().sortedBy { (_, value) ->
            value
        }
        //todo this is ugly might have bugs
        if (sortStops.isEmpty())
            return Stop.empty()
        else
            return sortStops[0].first

    }

}