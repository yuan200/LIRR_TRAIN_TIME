package com.yuan.nyctransit.features.lirr

import android.content.Context
import com.yuan.nyctransit.UseCase
import com.yuan.nyctransit.core.exception.Failure
import com.yuan.nyctransit.core.functional.Either
import javax.inject.Inject

class GetLirrGtfs
@Inject constructor(private val lirrGtfsRepository: LirrGtfsRepository,
                    private val context: Context) : UseCase<LirrGtfs, Boolean>(){

    override suspend fun run(params: Boolean): Either<Failure, LirrGtfs> = lirrGtfsRepository.lirrGtfs(context)
}