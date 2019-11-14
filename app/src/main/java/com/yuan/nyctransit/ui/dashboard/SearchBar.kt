package com.yuan.nyctransit.ui.dashboard

import android.content.Context
import android.graphics.Color
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

    var myLocation = ImageButton(context).apply {
        id = View.generateViewId()
        setBackgroundColor(Color.TRANSPARENT)
        setImageResource(R.drawable.ic_location_searching_24px)
        addView(this)
    }

    var descriptor = TextView(context).apply {
        id = View.generateViewId()
        text = "Options near"
        addView(this)
    }

    var searchTextView = TextView(context).apply {
        id = View.generateViewId()
        addView(this)
    }

    init {

        val set = ConstraintSet().also {
            it.clone(this)
        }

        set.connect(myLocation.id, ConstraintSet.LEFT, ConstraintSet.PARENT_ID, ConstraintSet.LEFT,20)
        set.connect(descriptor.id, ConstraintSet.LEFT, myLocation.id, ConstraintSet.RIGHT)

        set.connect(searchTextView.id, ConstraintSet.LEFT, myLocation.id, ConstraintSet.RIGHT)
        set.connect(searchTextView.id, ConstraintSet.TOP, descriptor.id, ConstraintSet.BOTTOM)

        set.applyTo(this)
    }

    fun setDisplayAddress(address: String) {
        searchTextView.text = address
    }
}