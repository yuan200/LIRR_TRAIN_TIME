package com.yuan.nyctransit.ui.dashboard

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.yuan.nyctransit.features.lirr.GetLirrFeed
import com.yuan.nyctransit.features.lirr.StopTimeUpdateView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

class NearbyViewModel
@Inject constructor(application: Application, val lirrFeed: GetLirrFeed) :
    AndroidViewModel(application) {


    private val _text = MutableLiveData<String>().apply {
        value = "This is dashboard Fragment"
    }
    val text: LiveData<String> = _text

    private val _feed =
        MutableLiveData<MutableList<StopTimeUpdateView>>().apply {
            //todo hardcode
            lirrFeed.stopId = "132"
            //todo viewmodeScope is use main thread, how to avoid useing CoroutineScope(Dispatchers.IO)
            lirrFeed(CoroutineScope(Dispatchers.IO), true) {
                if (it.isRight) value =
                    it.either(
                        {},
                        { stopTimeUpdateViewList -> stopTimeUpdateViewList }) as MutableList<StopTimeUpdateView>
            }
        }

    fun getFeed() = _feed


}