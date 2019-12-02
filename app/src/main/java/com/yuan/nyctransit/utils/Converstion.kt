package com.yuan.nyctransit.utils

import android.content.Context

fun dpToPx(context: Context, dp: Int): Float =
    dp * context.resources.displayMetrics.density