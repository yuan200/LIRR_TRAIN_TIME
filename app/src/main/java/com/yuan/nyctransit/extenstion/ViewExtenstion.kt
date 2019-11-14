package com.yuan.nyctransit.extenstion

import android.view.View
import android.view.ViewGroup

fun View.setMargins(view: View, left: Int = 0, top: Int = 0, right: Int = 0, bottom: Int = 0) {
    if (view.layoutParams is ViewGroup.MarginLayoutParams) {
        val p = view.layoutParams as ViewGroup.MarginLayoutParams
        p.setMargins(left, top, right, bottom)
        view.requestLayout()
    }
}