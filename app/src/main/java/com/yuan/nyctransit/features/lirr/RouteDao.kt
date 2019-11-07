package com.yuan.nyctransit.features.lirr

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import com.yuan.nyctransit.core.database.Route

@Dao
interface RouteDao {

    @Insert(onConflict = REPLACE)
    fun insert(route: Route)

    @Query("SELECT route_color FROM route where route_id = :routeId")
    fun getRouteColor(routeId: String): String
}