package com.yuan.nyctransit.ui.dashboard

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.ImageButton
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import com.yuan.nyctransit.R

class SearchBar :ConstraintLayout {

    @JvmOverloads
    constructor(
        context: Context,
        attrs: AttributeSet? = null,
        defStyleAttr: Int = 0
    ) : super(context, attrs, defStyleAttr)

    var locateCurrentLocation: ImageButton

    var descriptor: TextView

    var searchTextView: TextView

    init {
        locateCurrentLocation = ImageButton(context).apply {
            id = View.generateViewId()
            setImageResource(R.drawable.ic_location_searching_24px)
        }
        addView(locateCurrentLocation)

        descriptor = TextView(context).apply {
            id = View.generateViewId()
            text = "Options near"
        }
        addView(descriptor)

        searchTextView = TextView(context).apply {
            id = View.generateViewId()
        }
        addView(searchTextView)

        val set = ConstraintSet().also {
            it.clone(this)
        }

        set.connect(descriptor.id, ConstraintSet.LEFT, locateCurrentLocation.id, ConstraintSet.RIGHT)

        set.connect(searchTextView.id, ConstraintSet.LEFT, locateCurrentLocation.id, ConstraintSet.RIGHT)
        set.connect(searchTextView.id, ConstraintSet.TOP, descriptor.id, ConstraintSet.BOTTOM)

        set.applyTo(this)
    }

    fun setDisplayAddress(address: String) {
        searchTextView.text = address
    }
}