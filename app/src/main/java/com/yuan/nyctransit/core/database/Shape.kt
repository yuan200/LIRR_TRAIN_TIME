package com.yuan.nyctransit.core.database

data class Shape(val shapeId : String,
                 val shapePt_lat : String,
                 val shapePt_lon : String,
                 val shapePt_sequence : Int) {
}