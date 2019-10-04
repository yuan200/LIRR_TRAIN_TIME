package com.yuan.nyctransit.ui.dashboard

import android.location.Location
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.yuan.nyctransit.R
import com.yuan.nyctransit.core.database.Stop

class MapFragment : Fragment() {

    private lateinit var dashboardViewModel: DashboardViewModel

    private lateinit var mMap: GoogleMap

    private lateinit var fusedLocationClient: FusedLocationProviderClient

    private lateinit var currentLocation: Location

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dashboardViewModel =
            ViewModelProviders.of(this).get(DashboardViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_dashboard, container, false)
        val supportMapFragment = childFragmentManager.findFragmentById(R.id.map_fragment)
                as SupportMapFragment
        supportMapFragment.getMapAsync(OnMapReadyCallback {
            mMap = it


        })
        return root
    }

    override fun onResume() {
        super.onResume()
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(context!!).apply {
            lastLocation.addOnSuccessListener {
                currentLocation = it ?: Location("empty").apply {
                    latitude = 0.0
                    longitude = 0.0
                }

                val latlng = LatLng(currentLocation.latitude, currentLocation.longitude)
                mMap.addMarker(MarkerOptions().position(latlng).title("Maker in Sydney"))
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latlng, 12f))
            }
        }

    }
}