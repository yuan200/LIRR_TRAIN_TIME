package com.yuan.nyctransit.ui.dashboard

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageButton
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
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
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.yuan.nyctransit.R
import com.yuan.nyctransit.databinding.FragmentNearbyBinding
import com.yuan.nyctransit.features.lirr.LirrFeed
import com.yuan.nyctransit.features.lirr.ScheduleAdapter
import com.yuan.nyctransit.features.lirr.StopTimeUpdateView
import dagger.android.support.AndroidSupportInjection
import timber.log.Timber
import java.io.IOException
import java.util.*
import javax.inject.Inject

class NearbyFragment : Fragment() {

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

    @Inject lateinit var nearbyViewModelFactory: ViewModelProvider.AndroidViewModelFactory

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        AndroidSupportInjection.inject(this)

        val binding = DataBindingUtil.inflate<FragmentNearbyBinding>(inflater, R.layout.fragment_nearby, container,false)
        binding.lifecycleOwner = this
        binding.viewModel = nearbyViewModel
        val root = binding.root
//        addressSearchBar = root.findViewById(R.id.address_search_bar)

        root.findViewById<ImageButton>(R.id.searchButton).setOnClickListener {
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(LatLng(currentUserLocation.latitude, currentUserLocation.longitude),16f))
            nearbyViewModel.isMarkerOnCurrentLocation.value = true
        }

        fetchIndicatorView = root.findViewById(R.id.fetching_indicator_view)

        shimmerViewContainer = root.findViewById(R.id.shimmer_view_container)

        shimmerView = root.findViewById(R.id.shimmer_view)

//        (activity as MainActivity).navView.post {
//            val bottomNavHeight = (activity as MainActivity).navView.measuredHeight
//            val layoutParam = (shimmerView.layoutParams as ViewGroup.MarginLayoutParams).apply {
//                bottomMargin = bottomNavHeight
//            }
//            shimmerView.layoutParams = layoutParam
//        }


        //todo study clean architecure simple's extension usage
//        nearbyViewModel =
//            ViewModelProviders.of(this,nearbyViewModelFactory)[NearbyViewModel::class.java]

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
                    nearbyViewModel.currentLocation.observe(this@NearbyFragment, Observer {
                        currentMapLocation = it
                    })

                    nearbyViewModel.feed.observe(this@NearbyFragment, Observer {
                        shimmerView.stopShimmer()
                        shimmerViewContainer.visibility = View.GONE
                        viewAdapter.collection = it?: mutableListOf(StopTimeUpdateView.placeHolder())
                    })
                }

                val latlng = LatLng(currentMapLocation.latitude, currentMapLocation.longitude)
                //todo sometime multiple marker appears
                mMap.addMarker(MarkerOptions()
                    .icon(bitmapDescriptorFromVector(context as Context, R.drawable.circle_drawable))
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
                    val geocoder = Geocoder(context)
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

                nearbyViewModel.fetchingState.observe(this@NearbyFragment, Observer {
                    when(it) {
                        true -> fetchIndicatorView.visibility = View.VISIBLE
                        false -> fetchIndicatorView.visibility = View.GONE
                    }
                })
            }
        }

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