package com.yuan.nyctransit

import android.content.Intent
import android.os.Bundle
import android.widget.ProgressBar
import androidx.lifecycle.ViewModelProviders
import com.yuan.nyctransit.features.lirr.LirrViewModel
import com.yuan.nyctransit.features.lirr.LirrViewModelFactory
import com.yuan.nyctransit.platform.PermissionsActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.launch

class LoadingActivity : PermissionsActivity() {

    lateinit var model: LirrViewModel

    lateinit var progressBar: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_loading)

        progressBar = findViewById(R.id.LoadingProgressBar)

        model = ViewModelProviders.of(this, LirrViewModelFactory(application))[LirrViewModel::class.java]
    }

    override fun onResume() {
        super.onResume()
        val channel = Channel<Int>()
        lirrGtfs.channel = channel
        lirrGtfs(CoroutineScope(Dispatchers.Default), true)
        repeat(10) {
            CoroutineScope(Dispatchers.Main).launch {
                var progress = channel.receive()
                progressBar.progress = progress

                if (progress == 100) {
                    val intent = Intent(this@LoadingActivity, MainActivity::class.java)
                    startActivity(intent)
                }

            }
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
