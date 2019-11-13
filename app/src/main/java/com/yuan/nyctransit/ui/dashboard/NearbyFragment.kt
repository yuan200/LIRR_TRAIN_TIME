package com.yuan.nyctransit.ui.dashboard

import android.location.Geocoder
import android.location.Location
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageButton
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.facebook.shimmer.ShimmerFrameLayout
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.yuan.nyctransit.MainActivity
import com.yuan.nyctransit.R
import com.yuan.nyctransit.features.lirr.LirrFeed
import com.yuan.nyctransit.features.lirr.ScheduleAdapter
import com.yuan.nyctransit.features.lirr.StopTimeUpdateView
import dagger.android.support.AndroidSupportInjection
import javax.inject.Inject

class NearbyFragment : Fragment() {

    private lateinit var nearbyViewModel: NearbyViewModel

    private lateinit var mMap: GoogleMap

    private lateinit var fusedLocationClient: FusedLocationProviderClient

    private lateinit var currentLocation: Location

    private lateinit var latestLocation: Location

    private lateinit var recyclerView: RecyclerView

    private lateinit var viewAdapter: ScheduleAdapter

    private lateinit var shimmerViewContainer: CardView

    private lateinit var shimmerView: ShimmerFrameLayout

    private var alreadySubscribeFeed = false

    private lateinit var viewManager: RecyclerView.LayoutManager

    private lateinit var addressSearchBar: SearchBar

    private lateinit var fetchIndicatorView: FrameLayout

    private lateinit var goBackToCurrentLocation: ImageButton

    @Inject lateinit var nearbyViewModelFactory: ViewModelProvider.AndroidViewModelFactory

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        AndroidSupportInjection.inject(this)

        val root = inflater.inflate(R.layout.fragment_nearby, container, false)

        addressSearchBar = root.findViewById(R.id.address_search_bar)

        addressSearchBar.locateCurrentLocation.setOnClickListener {
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(LatLng(latestLocation.latitude, latestLocation.longitude), 15f))
        }

        fetchIndicatorView = root.findViewById(R.id.fetching_indicator_view)

        shimmerViewContainer = root.findViewById(R.id.shimmer_view_container)

        shimmerView = root.findViewById(R.id.shimmer_view)

        (activity as MainActivity).navView.post {
            val bottomNavHeight = (activity as MainActivity).navView.measuredHeight
            val layoutParam = (shimmerView.layoutParams as ViewGroup.MarginLayoutParams).apply {
                bottomMargin = bottomNavHeight
            }
            shimmerView.layoutParams = layoutParam
        }


        //todo study clean architecure simple's extension usage
        nearbyViewModel =
            ViewModelProviders.of(this,nearbyViewModelFactory)[NearbyViewModel::class.java]

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

        shimmerView.startShimmer()

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(context!!).apply {
            lastLocation.addOnSuccessListener {
                currentLocation = it ?: Location("empty").apply {
                    latitude = 0.0
                    longitude = 0.0
                }

                latestLocation = it ?: Location("empty").apply {
                    latitude = 0.0
                    longitude = 0.0
                }

                nearbyViewModel.currentLocation.value = currentLocation

                if (!alreadySubscribeFeed) {
                    alreadySubscribeFeed = true
                    nearbyViewModel.currentLocation.observe(this@NearbyFragment, Observer {
                        currentLocation = it
                    })

                    nearbyViewModel.feed.observe(this@NearbyFragment, Observer {
                        shimmerView.stopShimmer()
                        shimmerViewContainer.visibility = View.GONE
                        viewAdapter.collection = it?: mutableListOf(StopTimeUpdateView.placeHolder())
                    })
                }

                val latlng = LatLng(currentLocation.latitude, currentLocation.longitude)
                mMap.addMarker(MarkerOptions().position(latlng).title("Maker in Sydney"))
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latlng, 15f))
                mMap.setOnCameraIdleListener {
                    val middleLatLng = mMap.cameraPosition.target
                    nearbyViewModel.currentLocation.value = Location("what provider").apply {
                        latitude = middleLatLng.latitude
                        longitude = middleLatLng.longitude
                    }
                    nearbyViewModel.refreshSchedualView()
                    val geocoder = Geocoder(context)
                    val geoResult = geocoder.getFromLocation(middleLatLng.latitude, middleLatLng.longitude, 1)
                    val streeNum = geoResult[0].subThoroughfare?: ""
                    val stree = geoResult[0].thoroughfare?: ""
                    val displayAddress = streeNum + " " + stree
                    addressSearchBar.setDisplayAddress(displayAddress)
                    nearbyViewModel.fetchingState.value = false
                }

                mMap.setOnCameraMoveStartedListener {
                    nearbyViewModel.fetchingState.value = true
                }

                nearbyViewModel.fetchingState.observe(this@NearbyFragment, Observer {
                    when(it) {
                        true -> fetchIndicatorView.visibility = View.VISIBLE
                        false -> fetchIndicatorView.visibility = View.GONE
                    }
                })
            }
        }

    }
}