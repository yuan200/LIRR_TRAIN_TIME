package com.yuan.nyctransit.core.database

data class Shape(var shapeId : String,
                 var shapePt_lat : String,
                 var shapePt_lon : String,
                 var shapePt_sequence : Int) {
}