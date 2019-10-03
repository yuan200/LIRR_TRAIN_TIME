package com.yuan.nyctransit

import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.yuan.nyctransit.core.database.LirrGtfsBase
import com.yuan.nyctransit.core.di.ApplicationComponent
import com.yuan.nyctransit.features.lirr.GetLirrGtfs
import com.yuan.nyctransit.features.lirr.LirrGtfsRepository
import com.yuan.nyctransit.features.lirr.LirrViewModel
import com.yuan.nyctransit.features.lirr.LirrViewModelFactory
import com.yuan.nyctransit.platform.PermissionsActivity
import dagger.android.AndroidInjection
import timber.log.Timber
import javax.inject.Inject

class MainActivity : PermissionsActivity(){
    lateinit var model: LirrViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val navView: BottomNavigationView = findViewById(R.id.nav_view)

        model = ViewModelProviders.of(this, LirrViewModelFactory(this))[LirrViewModel::class.java]
//        model.getLirrGtfsRevised()


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
        val db = LirrGtfsBase.getInstance(this)
//        val revised = model.getLirrGtfsRevised()
        val revised = model.revised
        if (revised.value.isNullOrEmpty()) {
            Timber.i("revised is null")
            lirrGtfs(true)
        } else
            Timber.i("skip getRevised")
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        permissionsManager.processResult(requestCode, permissions, grantResults)
    }
}
