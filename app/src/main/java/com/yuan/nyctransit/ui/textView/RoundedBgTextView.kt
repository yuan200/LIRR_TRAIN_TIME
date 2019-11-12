package com.yuan.nyctransit.ui.textView

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatTextView

class RoundedBgTextView: AppCompatTextView {

    @JvmOverloads
    constructor(
        context: Context,
        attrs: AttributeSet? = null,
        defStyleAttr: Int = android.R.attr.textViewStyle
    ): super(context, attrs, defStyleAttr)

    init {
        setPadding(8,8,8,8)
    }

    override fun onDraw(canvas: Canvas?) {
        canvas?.apply {
            val paint = Paint().apply {
                color = Color.WHITE
            }
            val rectF = RectF(
                0f,
                0f,
                canvas.width.toFloat(),
                canvas.height.toFloat()
            )
            canvas.drawRoundRect(rectF,
                10f,
                10f,
                paint)
        }
        super.onDraw(canvas)
    }
}