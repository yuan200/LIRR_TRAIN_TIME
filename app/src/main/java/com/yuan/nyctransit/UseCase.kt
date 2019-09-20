package com.yuan.nyctransit

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

abstract class UseCase<out Type, in Params> where Type: Any {

    abstract suspend fun run(params: Params): Either<Failure, Type>

    /** todo
     * origin code is below, my code doesn't have CommonPool and UI, is this ok
     * val job = async(CommonPool) { run(params) }
     * launch(UI) { onResult(job.await()) }
     */
    operator fun invoke(params: Params, onResult: (Either<Failure, Type>) -> Unit = {}) {
        val job = GlobalScope.async { run(params) }
        GlobalScope.launch { onResult(job.await()) }
    }

    class None
}