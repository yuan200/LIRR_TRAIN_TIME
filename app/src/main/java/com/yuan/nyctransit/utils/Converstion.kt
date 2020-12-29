package com.yuan.nyctransit.utils

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.util.DisplayMetrics
import androidx.core.content.ContextCompat
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.BitmapDescriptorFactory


fun dpToPx(context: Context, dp: Int): Float =
    dp * context.resources.displayMetrics.density

fun  bitmapDescriptorFromVector(context: Context, vectorResId: Int): BitmapDescriptor {
    var vectorDrawable = ContextCompat.getDrawable(context, vectorResId);
    vectorDrawable!!.setBounds(
        0,
        0,
        vectorDrawable.getIntrinsicWidth(),
        vectorDrawable.getIntrinsicHeight()
    );
    var bitmap = Bitmap.createBitmap(
        vectorDrawable.getIntrinsicWidth(),
        vectorDrawable.getIntrinsicHeight(),
        Bitmap.Config.ARGB_8888
    );
    var canvas =  Canvas(bitmap);
    vectorDrawable.draw(canvas);
    return BitmapDescriptorFactory.fromBitmap(bitmap);
}

fun convertPixelsToDp(px: Float, context: Context): Float {
    return px / (context.resources.displayMetrics.densityDpi.toFloat() / DisplayMetrics.DENSITY_DEFAULT)
}

