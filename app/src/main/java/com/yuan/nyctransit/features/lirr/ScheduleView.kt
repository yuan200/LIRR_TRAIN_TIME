package com.yuan.nyctransit.features.lirr

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.view.View
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import androidx.core.content.ContextCompat
import com.yuan.nyctransit.R

class ScheduleView: ConstraintLayout {

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    var tripHeadSignTV: TextView
    var scheduleTime: TextView
    var realTimeUpdateTV: TextView
    var minTV: TextView
    var stopTV: TextView
    var routeColor: String = "000000"
    set(value) {
        var intColor = Color.parseColor("#$value")
        field = value
        tripHeadSignTV.setTextColor(Color.WHITE)
        tripHeadSignTV.setBackgroundColor(intColor)
    }

    init {

        setBackgroundColor(ContextCompat.getColor(context, R.color.scheduleViewPrimaryColor))

        val lineBorder = View(context).apply {
            id = View.generateViewId()
            layoutParams = LayoutParams(LayoutParams.MATCH_PARENT, 1)
            setBackgroundColor(Color.BLACK)
        }

        addView(lineBorder)

        id = generateViewId()
        tripHeadSignTV = TextView(context).apply {
            id = generateViewId()
        }
        addView(tripHeadSignTV)

        scheduleTime = TextView(context).apply {
            id = View.generateViewId()
        }
        addView(scheduleTime)

        realTimeUpdateTV = TextView(context).apply {
            id = generateViewId()
        }
        addView(realTimeUpdateTV)

        minTV = TextView(context).apply {
            text = "min"
            id = View.generateViewId()
        }
        addView(minTV)

        stopTV = TextView(context).apply {
            id = View.generateViewId()
        }
        addView(stopTV)

        val set = ConstraintSet().also {
            it.clone(this)
        }

        set.connect(scheduleTime.id, ConstraintSet.TOP, tripHeadSignTV.id, ConstraintSet.BOTTOM, 10)
        set.connect(stopTV.id, ConstraintSet.TOP, scheduleTime.id, ConstraintSet.BOTTOM, 10)
        set.connect(realTimeUpdateTV.id, ConstraintSet.RIGHT, ConstraintSet.PARENT_ID, ConstraintSet.RIGHT, 30)
        set.connect(minTV.id, ConstraintSet.TOP, realTimeUpdateTV.id, ConstraintSet.BOTTOM)
        set.connect(minTV.id, ConstraintSet.RIGHT, ConstraintSet.PARENT_ID, ConstraintSet.RIGHT, 30)
        set.applyTo(this)

    }

}