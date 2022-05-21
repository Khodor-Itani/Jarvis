package com.kdz.jarvis.ui.common

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.StringRes
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isGone
import androidx.transition.TransitionManager
import com.kdz.jarvis.R

private val IGNORED_VIEW_IDS = arrayOf(R.id.textView_title, R.id.imageView_collapse_indicator, R.id.constraintLayout_container, R.id.barrier_title_bottom)

class CollapsibleViewContainer
@JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
    defStyleRes: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr, defStyleRes) {

    private val titleTextView: TextView
    private val collapseIndicatorImageView: ImageView
    private val containerConstriantLayout: ConstraintLayout

    var isExpanded = false

    init {
        inflate(context, R.layout.view_collapsible_view_container, this)

        titleTextView = findViewById(R.id.textView_title)
        collapseIndicatorImageView = findViewById(R.id.imageView_collapse_indicator)
        containerConstriantLayout = findViewById(R.id.constraintLayout_container)

        obtainAttributes(attrs)

        setExpanded(false, false)

        val toggleExpandClickListener = OnClickListener {
            setExpanded(!isExpanded)
        }

        titleTextView.setOnClickListener(toggleExpandClickListener)
        collapseIndicatorImageView.setOnClickListener(toggleExpandClickListener)
    }

    private fun obtainAttributes(attrs: AttributeSet?) {
        context.theme.obtainStyledAttributes(
            attrs, R.styleable.CollapsibleViewContainer, 0, 0).apply {

            try {
                if(hasValue(R.styleable.CollapsibleViewContainer_title)) {
                    val title = getString(R.styleable.CollapsibleViewContainer_title)
                    setTitle(title ?: "")
                }
            } finally {
                recycle()
            }
        }
    }

    override fun addView(child: View?, index: Int, params: ViewGroup.LayoutParams?) {
        val childId = child?.id ?: return

        if(IGNORED_VIEW_IDS.contains(childId)) {
            super.addView(child, index, params)
        } else {
            containerConstriantLayout.addView(child, index, params)
        }
    }

    fun setTitle(@StringRes title: Int) = titleTextView.setText(title)
    fun setTitle(title: CharSequence) {
        titleTextView.text = title
    }

    fun setExpanded(isExpanded: Boolean, animated: Boolean = true) {
        val rotation = if(isExpanded) 180F else 0F

        collapseIndicatorImageView.animate().rotation(rotation).setDuration(300L).start()

        if(animated) {
            TransitionManager.beginDelayedTransition(this)
        }
        containerConstriantLayout.isGone = !isExpanded

        this.isExpanded = isExpanded
    }

}