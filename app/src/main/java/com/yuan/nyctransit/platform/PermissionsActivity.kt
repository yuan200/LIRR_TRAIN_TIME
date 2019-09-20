package com.yuan.nyctransit.platform

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.yuan.nyctransit.AndroidApplication
import com.yuan.nyctransit.Permissions.PermissionsManager
import com.yuan.nyctransit.core.di.ApplicationComponent
import javax.inject.Inject

abstract class PermissionsActivity : AppCompatActivity() {

    @Inject
    lateinit var permissionsManager: PermissionsManager

    val appComponent: ApplicationComponent by lazy(mode = LazyThreadSafetyMode.NONE) {
        (this.application as AndroidApplication).appComponent
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        appComponent.inject(this)
        permissionsManager.attach(this)
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
