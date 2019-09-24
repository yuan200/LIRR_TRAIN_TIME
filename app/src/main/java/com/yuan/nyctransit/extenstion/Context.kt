package com.yuan.nyctransit.extenstion

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo

//todo NetworkInfo is deprecated
val Context.networkInfo: NetworkInfo? get() =
    (this.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager).activeNetworkInfo
