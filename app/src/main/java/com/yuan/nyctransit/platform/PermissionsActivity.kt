package com.yuan.nyctransit.platform

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.yuan.nyctransit.Permissions.PermissionsManager
import com.yuan.nyctransit.core.di.DummyClass
import com.yuan.nyctransit.features.lirr.GetLirrGtfs
import dagger.android.AndroidInjection
import javax.inject.Inject

abstract class PermissionsActivity : AppCompatActivity() {

    //todo this is not working if I put it inside main activity
    @Inject lateinit var lirrGtfs : GetLirrGtfs

    @Inject lateinit var permissionsManager: PermissionsManager

    @Inject lateinit var dummyObject: DummyClass

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AndroidInjection.inject(this)
        permissionsManager.attach(this)
        Log.i("wenistesting", dummyObject.printMessage())
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        permissionsManager.processResult(requestCode, permissions, grantResults)
    }

    override fun onDestroy() {
        permissionsManager.detach(this)
        super.onDestroy()
    }
}
