package com.yuan.nyctransit.features.lirr

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.view.View
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet

class ScheduleView: ConstraintLayout {

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    var trainNameTV: TextView
    var scheduleTime: TextView
    var trainTimeTV: TextView

    init {

        val lineBorder = View(context).apply {
            id = View.generateViewId()
            layoutParams = LayoutParams(LayoutParams.MATCH_PARENT, 1)
            setBackgroundColor(Color.BLACK)
        }

        addView(lineBorder)

        id = generateViewId()
        trainNameTV = TextView(context).apply {
            id = generateViewId()
        }
        addView(trainNameTV)

        scheduleTime = TextView(context).apply {
            id = View.generateViewId()
        }
        addView(scheduleTime)

        trainTimeTV = TextView(context).apply {
            id = generateViewId()
        }
        addView(trainTimeTV)

        val set = ConstraintSet().also {
            it.clone(this)
        }
//        set.connect(trainTimeTV.id, ConstraintSet.LEFT, trainNameTV.id, ConstraintSet.RIGHT)
//        set.createHorizontalChain(ConstraintSet.PARENT_ID, ConstraintSet.LEFT,ConstraintSet.PARENT_ID, ConstraintSet.RIGHT,
//            intArrayOf(trainNameTV.id, trainTimeTV.id), null, ConstraintSet.CHAIN_SPREAD)
        set.connect(trainTimeTV.id, ConstraintSet.RIGHT, ConstraintSet.PARENT_ID, ConstraintSet.RIGHT, 30)
        set.connect(scheduleTime.id, ConstraintSet.TOP, trainNameTV.id, ConstraintSet.BOTTOM, 10)
        set.applyTo(this)

    }

}