package com.yuan.nyctransit.core.exception

sealed class Failure {
    object NetworkConnection : Failure()
    object ServerError : Failure()

    /** * Extend this class for feature specific failures. */
    abstract class FeatureFailure: Failure()
}