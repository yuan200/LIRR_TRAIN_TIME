package com.yuan.nyctransit.core.database

data class Trip(val routeId : String,
                val serviceId : String,
                val tripHeadsign : String,
                val tripShortName : String,
                val directionId : String,
                val shapeId : String) {
}