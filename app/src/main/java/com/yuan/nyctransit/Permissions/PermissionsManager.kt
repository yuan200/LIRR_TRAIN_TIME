package com.yuan.nyctransit.Permissions

import android.Manifest
import android.app.Activity
import android.app.Application
import android.content.pm.PackageManager.PERMISSION_GRANTED
import androidx.annotation.VisibleForTesting
import androidx.annotation.VisibleForTesting.PRIVATE
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.yuan.nyctransit.extenstion.asString
import io.reactivex.Observable
import io.reactivex.Scheduler
import io.reactivex.Single
import io.reactivex.Single.just
import io.reactivex.subjects.PublishSubject
import timber.log.Timber

data class GrantResult(
    val permission: String,
    val granted: Boolean
)
/**
 * Helps us manage, check, and dispatch permission requests without much boiler plate in our
 * Activities
 */
interface PermissionsManager {
    fun onGrantResult(): Observable<GrantResult>

    fun attach(activity: Activity)

    fun hasLocationPermission(): Boolean

    fun requestLocationPermmission(waitForGranted: Boolean = false): Single<GrantResult>

    fun processResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray)

    fun detach(activity: Activity)
}

class PermissionManagerImpl(
    private val context: Application,
    private val mainScheduler: Scheduler
): PermissionsManager {

    companion object {
        @VisibleForTesting(otherwise = PRIVATE)
        const val REQUEST_CODE = 99
    }

    @VisibleForTesting(otherwise = PRIVATE)
    var activity: Activity? = null
    private val relay = PublishSubject.create<GrantResult>()

    override fun onGrantResult(): Observable<GrantResult> = relay.share().observeOn(mainScheduler)

    override fun attach(activity: Activity) {
        Timber.d("attach(): $activity")
        this.activity = activity
    }

    override fun hasLocationPermission(): Boolean = hasPermission(Manifest.permission.ACCESS_FINE_LOCATION)

    override fun requestLocationPermmission(waitForGranted: Boolean): Single<GrantResult> =
        requestPermission(REQUEST_CODE, Manifest.permission.ACCESS_FINE_LOCATION, waitForGranted)

    override fun processResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        Timber.d("processResult(): requestCode $requestCode, permissions: ${permissions.asString()}, " +
                "grantResults: ${grantResults.asString()}")
        for ((index, permission) in permissions.withIndex()) {
            val granted = grantResults[index] == PERMISSION_GRANTED
            val result = GrantResult(permission, granted)
            Timber.d("Permission grant result: $result")
            relay.onNext(result)
        }
    }

    override fun detach(activity: Activity) {
        if (this.activity === activity) {
            Timber.d("detach(): $activity")
            this.activity = null
        }
    }

    private fun hasPermission(permission: String): Boolean =
        ContextCompat.checkSelfPermission(context, permission) == PERMISSION_GRANTED

    private fun requestPermission(code: Int, permission: String, waitForGranted: Boolean): Single<GrantResult> {
        Timber.d("Requesting permission: %s", permission)
        if (hasPermission(permission)) {
            Timber.d("Already have this permission!")
            return just(GrantResult(permission, true).also {
                relay.onNext(it)
            })
        }

        val attachedTo = activity ?: throw IllegalStateException("Not attached to activity")
        ActivityCompat.requestPermissions(attachedTo, arrayOf(permission), code)
        return onGrantResult()
            .filter { it.permission == permission }
            .filter {
                if (waitForGranted) {
                    // If we are waitting for granted, only allow emission if granted is true
                    it.granted
                } else {
                    //else continue
                    true
                }
            }
            .take(1)
            .singleOrError()
    }
}