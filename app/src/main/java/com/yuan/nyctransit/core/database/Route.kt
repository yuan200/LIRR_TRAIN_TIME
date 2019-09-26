package com.yuan.nyctransit.core.database

data class Route(val routeId : String,
                 val routeShort_name : String?,
                 val routeLong_name : String,
                 val routeType : String,
                 val routeColor : String,
                 val routeText_color : String) {
}