package com.yuan.nyctransit.ui.drawable

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View

class CircleDrawableView: View {

    @JvmOverloads
    constructor(context: Context,
                attrs: AttributeSet? = null,
                defStyleAttr: Int = 0
    ): super(context, attrs, defStyleAttr)

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        canvas.apply {
            val pointX = canvas.width / 2f
            val pointY = canvas.height /2f
            val paint = Paint().apply {
                color = Color.BLUE
                isAntiAlias = true
            }
            drawCircle(pointX, pointY, 15f, paint)
        }
    }

}