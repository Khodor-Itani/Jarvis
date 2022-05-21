package com.kdz.jarvis.ui.common

import android.content.Context
import android.util.AttributeSet
import android.widget.LinearLayout
import androidx.appcompat.widget.AppCompatTextView
import com.kdz.jarvis.R
import kotlin.properties.Delegates

class SimpleTextListView
@JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
    defStyleRes: Int = 0
) : LinearLayout(context, attrs, defStyleAttr, defStyleRes) {

    init {
        orientation = VERTICAL
    }

    var texts: List<CharSequence> by Delegates.observable(listOf()) { _, _, newValue ->
        onTextListChanged(newValue)
    }

    private fun onTextListChanged(texts: List<CharSequence>) {
        removeAllViews()

        texts.map {
            AppCompatTextView(context).apply {
                text = it
            }
        }.forEachIndexed { index, textView ->
            val layoutParams = LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT)
            if (index != 0) {
                layoutParams.topMargin =
                    context.resources.getDimensionPixelSize(R.dimen.text_spacing_vertical)
            }
            addView(textView, layoutParams)
        }
    }

}