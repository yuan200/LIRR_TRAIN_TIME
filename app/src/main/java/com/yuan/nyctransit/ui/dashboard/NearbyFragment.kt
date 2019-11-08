package com.yuan.nyctransit.ui.dashboard

import android.location.Location
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.yuan.nyctransit.R
import com.yuan.nyctransit.features.lirr.LirrFeed
import com.yuan.nyctransit.features.lirr.ScheduleAdapter
import dagger.android.support.AndroidSupportInjection
import javax.inject.Inject

class NearbyFragment : Fragment() {

    private lateinit var nearbyViewModel: NearbyViewModel

    private lateinit var mMap: GoogleMap

    private lateinit var fusedLocationClient: FusedLocationProviderClient

    private lateinit var currentLocation: Location

    private lateinit var recyclerView: RecyclerView

    private lateinit var viewAdapter: ScheduleAdapter

    private var alreadySubscribeFeed = false

    private lateinit var viewManager: RecyclerView.LayoutManager

    @Inject lateinit var nearbyViewModelFactory: ViewModelProvider.AndroidViewModelFactory

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        AndroidSupportInjection.inject(this)
        //todo study clean architecure simple's extension usage
        nearbyViewModel =
            ViewModelProviders.of(this,nearbyViewModelFactory)[NearbyViewModel::class.java]






        val root = inflater.inflate(R.layout.fragment_map, container, false)
        val supportMapFragment = childFragmentManager.findFragmentById(R.id.map_fragment)
                as SupportMapFragment
        supportMapFragment.getMapAsync(OnMapReadyCallback {
            mMap = it


        })

        viewManager = LinearLayoutManager(context)

        val adapterList = LirrFeed.stopTimeUpdateViewList
        viewAdapter = ScheduleAdapter()

        recyclerView = root.findViewById<RecyclerView>(R.id.schedule_recycler_view).apply {
            setHasFixedSize(true)
            layoutManager = viewManager
            adapter = viewAdapter
        }
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

                nearbyViewModel.currentLocation.value = currentLocation

                if (!alreadySubscribeFeed) {
                    alreadySubscribeFeed = true
                    nearbyViewModel.currentLocation.observe(this@NearbyFragment, Observer {
                        currentLocation = it
                    })

                    nearbyViewModel.getFeed().observe(this@NearbyFragment, Observer {
                        viewAdapter.collection = it
                    })
                }

                val latlng = LatLng(currentLocation.latitude, currentLocation.longitude)
                mMap.addMarker(MarkerOptions().position(latlng).title("Maker in Sydney"))
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latlng, 12f))
            }
        }

    }
}