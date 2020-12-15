package com.yuan.nyctransit.features.lirr.search

import android.location.Location
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SearchResultViewModel: ViewModel() {
    val location = MutableLiveData<Location>()
}