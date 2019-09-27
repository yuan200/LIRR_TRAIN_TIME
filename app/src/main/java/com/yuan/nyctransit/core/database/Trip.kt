package com.yuan.nyctransit.core.database

data class Trip(var routeId : String,
                var serviceId : String,
                var tripHeadsign : String,
                var tripShortName : String,
                var directionId : String,
                var shapeId : String) {
}