package com.yuan.nyctransit

import android.os.Bundle
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.yuan.nyctransit.core.di.ApplicationComponent
import com.yuan.nyctransit.features.lirr.GetLirrGtfs
import com.yuan.nyctransit.features.lirr.LirrGtfsRepository
import com.yuan.nyctransit.platform.PermissionsActivity
import dagger.android.AndroidInjection
import timber.log.Timber
import javax.inject.Inject

class MainActivity : PermissionsActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val navView: BottomNavigationView = findViewById(R.id.nav_view)


        val navController = findNavController(R.id.nav_host_fragment)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        //request location permission
        if (!permissionsManager.hasLocationPermission()) {
            permissionsManager.requestLocationPermmission()
        }
    }

    override fun onResume() {
        super.onResume()
        lirrGtfs(true)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        permissionsManager.processResult(requestCode, permissions, grantResults)
    }
}
