package com.yuan.nyctransit.ui.dashboard

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.yuan.nyctransit.features.lirr.GetLirrFeed
import com.yuan.nyctransit.features.lirr.NetworkLirrRepository
import javax.inject.Inject

//todo is inject dependency here a good practice
class NearbyViewModel
@Inject constructor(application: Application, lirrFeed: GetLirrFeed) :
    AndroidViewModel(application) {

    private val _text = MutableLiveData<String>().apply {
        value = "This is dashboard Fragment"
    }
    val text: LiveData<String> = _text

    private val lirrFeed = GetLirrFeed(NetworkLirrRepository(), application)
}