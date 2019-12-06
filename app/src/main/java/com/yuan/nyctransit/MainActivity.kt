package com.yuan.nyctransit

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.os.Bundle
import android.view.View
import android.widget.FrameLayout
import android.widget.ImageButton
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
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
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.yuan.nyctransit.databinding.ActivityMainBinding
import com.yuan.nyctransit.features.lirr.*
import com.yuan.nyctransit.platform.PermissionsActivity
import com.yuan.nyctransit.ui.dashboard.NearbyViewModel
import dagger.android.AndroidInjection
import timber.log.Timber
import java.io.IOException
import java.util.*
import javax.inject.Inject

class MainActivity : PermissionsActivity() {
    lateinit var model: LirrViewModel

    private val nearbyViewModel: NearbyViewModel by lazy {
        ViewModelProviders.of(this, nearbyViewModelFactory)[NearbyViewModel::class.java]
    }

    private lateinit var mMap: GoogleMap

    private lateinit var fusedLocationClient: FusedLocationProviderClient

    private lateinit var currentMapLocation: Location

    private lateinit var currentUserLocation: Location

    private lateinit var recyclerView: RecyclerView

    private lateinit var viewAdapter: ScheduleAdapter

    private lateinit var shimmerViewContainer: CardView

    private lateinit var shimmerView: ShimmerFrameLayout

    private var alreadySubscribeFeed = false

    private lateinit var viewManager: RecyclerView.LayoutManager

//    private lateinit var addressSearchBar: SearchBar

    private lateinit var fetchIndicatorView: FrameLayout

    private lateinit var goBackToCurrentLocation: ImageButton

    @Inject
    lateinit var nearbyViewModelFactory: ViewModelProvider.AndroidViewModelFactory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AndroidInjection.inject(this)

        val binding: ActivityMainBinding = DataBindingUtil.setContentView(
            this, R.layout.activity_main
        )
        binding.lifecycleOwner = this
        binding.viewModel = nearbyViewModel

        model = ViewModelProviders.of(
            this,
            LirrViewModelFactory(application)
        )[LirrViewModel::class.java]

        //request location permission
        if (!permissionsManager.hasLocationPermission()) {
            permissionsManager.requestLocationPermmission()
        }

        findViewById<ImageButton>(R.id.searchButton).setOnClickListener {
            mMap.animateCamera(
                CameraUpdateFactory.newLatLngZoom(
                    LatLng(
                        currentUserLocation.latitude,
                        currentUserLocation.longitude
                    ), 16f
                )
            )
            nearbyViewModel.isMarkerOnCurrentLocation.value = true
        }
        fetchIndicatorView = findViewById(R.id.fetching_indicator_view)

        shimmerViewContainer = findViewById(R.id.shimmer_view_container)

        shimmerView = findViewById(R.id.shimmer_view)

        val supportMapFragment = supportFragmentManager.findFragmentById(R.id.map_fragment)
                as SupportMapFragment
        supportMapFragment.getMapAsync(OnMapReadyCallback {
            mMap = it
        })
        viewManager = LinearLayoutManager(this)

        val adapterList = LirrFeed.stopTimeUpdateViewList
        viewAdapter = ScheduleAdapter()

        recyclerView = findViewById<RecyclerView>(R.id.schedule_recycler_view).apply {
            setHasFixedSize(true)
            layoutManager = viewManager
            adapter = viewAdapter
        }


    }

    override fun onResume() {
        super.onResume()
        shimmerView.startShimmer()

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this).apply {
            lastLocation.addOnSuccessListener {
                currentMapLocation = it ?: Location("empty").apply {
                    latitude = 0.0
                    longitude = 0.0
                }

                currentUserLocation = it ?: Location("empty").apply {
                    latitude = 0.0
                    longitude = 0.0
                }

                nearbyViewModel.currentLocation.value = currentMapLocation

                if (!alreadySubscribeFeed) {
                    alreadySubscribeFeed = true
                    nearbyViewModel.currentLocation.observe(this@MainActivity, Observer {
                        currentMapLocation = it
                    })

                    nearbyViewModel.feed.observe(this@MainActivity, Observer {
                        shimmerView.stopShimmer()
                        shimmerViewContainer.visibility = View.GONE
                        viewAdapter.collection = it?: mutableListOf(StopTimeUpdateView.placeHolder())
                    })
                }

                val latlng = LatLng(currentMapLocation.latitude, currentMapLocation.longitude)
                //todo sometime multiple marker appears
                mMap.addMarker(
                    MarkerOptions()
                        .icon(bitmapDescriptorFromVector(this@MainActivity as Context, R.drawable.circle_drawable))
                        .position(latlng)
                        .title("You are here"))
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latlng, 16f))
                mMap.setOnCameraIdleListener {
                    val middleLatLng = mMap.cameraPosition.target
                    nearbyViewModel.currentLocation.value = Location("what provider").apply {
                        latitude = middleLatLng.latitude
                        longitude = middleLatLng.longitude
                    }
                    nearbyViewModel.refreshSchedualView()
                    val geocoder = Geocoder(this@MainActivity)
                    var geoResult: List<Address>
                    try {
                        geoResult = geocoder.getFromLocation(middleLatLng.latitude, middleLatLng.longitude, 1)
                    } catch (e: IOException) {
                        geoResult = listOf(Address(Locale.US))
                    }
                    if (geoResult.isNotEmpty()) {
                        var streeNum = geoResult[0].subThoroughfare?: ""
                        var stree = geoResult[0].thoroughfare?: ""
                        val displayAddress = streeNum + " " + stree
                        nearbyViewModel.displayAddress.value = displayAddress
                    }
                    nearbyViewModel.fetchingState.value = false
                }

                mMap.setOnCameraMoveStartedListener {
                    nearbyViewModel.fetchingState.value = true
                    nearbyViewModel.isMarkerOnCurrentLocation.value = false
                    Timber.i("camera was moved")
                }

                nearbyViewModel.fetchingState.observe(this@MainActivity, Observer {
                    when(it) {
                        true -> fetchIndicatorView.visibility = View.VISIBLE
                        false -> fetchIndicatorView.visibility = View.GONE
                    }
                })
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
    private fun  bitmapDescriptorFromVector(context: Context, vectorResId:Int): BitmapDescriptor {
        var vectorDrawable = ContextCompat.getDrawable(context, vectorResId);
        vectorDrawable!!.setBounds(0, 0, vectorDrawable.getIntrinsicWidth(), vectorDrawable.getIntrinsicHeight());
        var bitmap = Bitmap.createBitmap(vectorDrawable.getIntrinsicWidth(), vectorDrawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        var canvas =  Canvas(bitmap);
        vectorDrawable.draw(canvas);
        return BitmapDescriptorFactory.fromBitmap(bitmap);
    }
}
