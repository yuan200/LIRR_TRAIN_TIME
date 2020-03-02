package com.yuan.nyctransit.features.lirr.search

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.yuan.nyctransit.core.database.LirrGtfsBase
import com.yuan.nyctransit.core.database.Stop
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SearchViewModel(application: Application) : AndroidViewModel(application) {

    val stopList: MutableLiveData<MutableList<Stop>> by lazy {
        liveData@ MutableLiveData<MutableList<Stop>>().apply {
            CoroutineScope(Dispatchers.IO).launch {
                this@apply.value = LirrGtfsBase.getInstance(application)!!.stopDao().allStops() as MutableList<Stop>
            }

        }
    }
}
