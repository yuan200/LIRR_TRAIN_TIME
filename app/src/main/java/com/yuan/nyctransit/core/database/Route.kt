package com.yuan.nyctransit.core.database

data class Route(var routeId : String,
                 var routeShort_name : String?,
                 var routeLong_name : String,
                 var routeType : String,
                 var routeColor : String,
                 var routeText_color : String) {
}