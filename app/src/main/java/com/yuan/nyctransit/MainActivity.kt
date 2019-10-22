package com.yuan.nyctransit

import android.os.Bundle
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.yuan.nyctransit.core.database.LirrGtfsBase
import com.yuan.nyctransit.features.lirr.LirrViewModel
import com.yuan.nyctransit.features.lirr.LirrViewModelFactory
import com.yuan.nyctransit.platform.PermissionsActivity
import timber.log.Timber

class MainActivity : PermissionsActivity(){
    lateinit var model: LirrViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val navView: BottomNavigationView = findViewById(R.id.nav_view)

        model = ViewModelProviders.of(this, LirrViewModelFactory(application))[LirrViewModel::class.java]


        val navController = findNavController(R.id.nav_host_fragment)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_home, R.id.navigation_nearby, R.id.navigation_notifications
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
        //todo need to download this data in background after a certain of time
        //todo need to show loading when downloading data first time
        val revised = model.revised
//        if (revised.value.isNullOrEmpty()) {
//            lirrGtfs(CoroutineScope(Dispatchers.Default), true)
//        }

        val allStops = model.nearByStops.also {
            Timber.i(it.value.toString())
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
