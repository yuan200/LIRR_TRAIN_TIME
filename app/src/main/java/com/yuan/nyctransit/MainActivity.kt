package com.yuan.nyctransit

import android.os.Bundle
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.yuan.nyctransit.features.lirr.LirrViewModel
import com.yuan.nyctransit.features.lirr.LirrViewModelFactory
import com.yuan.nyctransit.platform.PermissionsActivity

class MainActivity : PermissionsActivity(){
    lateinit var model: LirrViewModel

    lateinit var navView: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        navView = findViewById(R.id.bottom_nav_view)
        val navController = findNavController(R.id.nav_host_fragment)
        navView.setupWithNavController(navController)

        model = ViewModelProviders.of(this, LirrViewModelFactory(application))[LirrViewModel::class.java]

        //request location permission
        if (!permissionsManager.hasLocationPermission()) {
            permissionsManager.requestLocationPermmission()
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        permissionsManager.processResult(requestCode, permissions, grantResults)
    }
}
