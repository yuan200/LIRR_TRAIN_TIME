package com.yuan.nyctransit

import com.yuan.nyctransit.core.exception.Failure
import com.yuan.nyctransit.core.functional.Either
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

//todo why Type: Any
abstract class UseCase<out Type, in Params> where Type: Any {

    abstract suspend fun run(params: Params): Either<Failure, Type>

    /** todo
     * origin code is below, my code doesn't have CommonPool and UI, is this ok
     * val job = async(CommonPool) { run(params) }
     * launch(UI) { onResult(job.await()) }
     */
    operator fun invoke(scope: CoroutineScope, params: Params, onResult: (Either<Failure, Type>) -> Unit = {}) {
        val job = scope.async { run(params) }
        CoroutineScope(Dispatchers.Main).launch { onResult(job.await()) }
    }


    class None
}