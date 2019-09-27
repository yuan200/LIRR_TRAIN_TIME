package com.yuan.nyctransit.core.database

data class Agency(var agencyId : String,
                  var agencyName : String,
                  var agencyUrl : String,
                  var agencyTimeZone : String,
                  var agencyLang : String,
                  var agencyPhone : String) {
}