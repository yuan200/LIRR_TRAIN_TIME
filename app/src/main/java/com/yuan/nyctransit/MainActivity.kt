package com.yuan.nyctransit

import android.app.ProgressDialog.show
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import timber.log.Timber

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val navView: BottomNavigationView = findViewById(R.id.nav_view)

        navView.setOnNavigationItemSelectedListener {it ->
            when (it.itemId) {
                R.id.navigation_home -> {
                    Log.d("navigation", "navigation_home")
                    Timber.d("navigation_home")
                    Toast.makeText(this, "navigation_honme", Toast.LENGTH_LONG).show()
//                    true
                }
                R.id.navigation_dashboard -> {
                    Toast.makeText(this, "navigation_dashboard", Toast.LENGTH_LONG).show()
//                    true
                }
                R.id.navigation_notifications -> {
                    Toast.makeText(this, "navigation_notifications", Toast.LENGTH_LONG).show()
//                    true
                }
                else -> {
                    Toast.makeText(this, "navigation_else", Toast.LENGTH_LONG).show()
//                    false
                }
            }
            return@setOnNavigationItemSelectedListener true
        }

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
    }
}
