package com.yuan.nyctransit.features.lirr.search

import com.yuan.nyctransit.UseCase
import com.yuan.nyctransit.core.exception.Failure
import com.yuan.nyctransit.core.functional.Either

class ShowStop: UseCase<UseCase.None, UseCase.None>() {
    override suspend fun run(params: None): Either<Failure, None> {
        //todo finish this
        return Either.Left(Failure.NetworkConnection)
    }
}