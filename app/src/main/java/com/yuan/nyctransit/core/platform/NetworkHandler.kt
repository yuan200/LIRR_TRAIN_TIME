package com.yuan.nyctransit.core.platform

import android.content.Context
import com.yuan.nyctransit.extenstion.networkInfo
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NetworkHandler
@Inject constructor(private val context: Context){
    val isConnected get() = context.networkInfo?.isConnectedOrConnecting
}