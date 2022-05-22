package com.kdz.jarvis.ui.common

import android.content.Context
import android.transition.TransitionManager
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isGone
import com.kdz.jarvis.R
import com.kdz.jarvis.repositories.result.Resource
import kotlin.properties.Delegates

private val IGNORED_VIEW_IDS = listOf(R.id.constraintLayout_container, R.id.loadingView)

class ResourceStateViewContainer
@JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
    defStyleRes: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr, defStyleRes) {

    val containerConstraintLayout: ConstraintLayout
    val loadingView: LoadingView

    init {
        inflate(context, R.layout.view_resource_state_container, this)

        containerConstraintLayout = findViewById(R.id.constraintLayout_container)
        loadingView = findViewById(R.id.loadingView)
    }

    var resource: Resource<*> by Delegates.observable(Resource.Loading) {_, _, newValue ->
        onResourceReceived(newValue)
    }

    private fun onResourceReceived(resource: Resource<*>) = when(resource) {
        Resource.Loading -> onResourceLoading()
        is Resource.Success -> onResourceSuccess()
        else -> {}
    }

    private fun onResourceLoading() {
        parent ?: return

        TransitionManager.beginDelayedTransition(parent as ViewGroup)
        containerConstraintLayout.isGone = true
        loadingView.isGone = false
    }

    private fun onResourceSuccess() {
        TransitionManager.beginDelayedTransition(parent as ViewGroup)
        containerConstraintLayout.isGone = false
        loadingView.isGone = true
    }

    override fun addView(child: View?, index: Int, params: ViewGroup.LayoutParams?) {
        val childId = child?.id ?: return

        if (IGNORED_VIEW_IDS.contains(childId)) {
            super.addView(child, index, params)
        } else {
            containerConstraintLayout.addView(child, index, params)
        }
    }
}