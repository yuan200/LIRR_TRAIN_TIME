package com.yuan.nyctransit.features.lirr

import com.yuan.nyctransit.core.database.Stop

interface ILocalLirrRepository {

    suspend fun getAllStops(): List<Stop>
}