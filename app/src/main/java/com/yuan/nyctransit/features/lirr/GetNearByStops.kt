package com.yuan.nyctransit.features.lirr

import com.yuan.nyctransit.UseCase
import com.yuan.nyctransit.core.database.Stop
import com.yuan.nyctransit.core.exception.Failure
import com.yuan.nyctransit.core.functional.Either

class GetNearByStops: UseCase<List<Stop>, List<Stop>>() {
    override suspend fun run(params: List<Stop>): Either<Failure, List<Stop>> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}
