package com.yuan.nyctransit.features.lirr

import android.content.Context
import com.yuan.nyctransit.UseCase
import com.yuan.nyctransit.core.exception.Failure
import com.yuan.nyctransit.core.functional.Either
import kotlinx.coroutines.channels.Channel
import javax.inject.Inject

class GetLirrGtfs
@Inject constructor(private val lirrGtfsRepository: LirrGtfsRepository,
                    private val context: Context) : UseCase<LirrGtfs, Boolean>(){

    lateinit var channel: Channel<Int>

    override suspend fun run(params: Boolean): Either<Failure, LirrGtfs> = lirrGtfsRepository.lirrGtfs(context, channel)
}