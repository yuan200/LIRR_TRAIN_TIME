package com.yuan.nyctransit.ui.textView

import android.content.Context
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatTextView

class ScheduledTextView: AppCompatTextView {

    @JvmOverloads
    constructor(
        context: Context,
        attrs: AttributeSet? = null,
        defStyleAttr: Int = android.R.attr.textViewStyle
    ): super(context, attrs, defStyleAttr)

    override fun setText(text: CharSequence?, type: BufferType?) {
        val scheduledText = "Scheduled \u2022 $text"
        super.setText(scheduledText, type)
    }
}