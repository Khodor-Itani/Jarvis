package com.kdz.jarvis.ui.databinding

import android.widget.ImageView
import androidx.core.view.isGone
import androidx.databinding.BindingAdapter
import coil.load
import coil.transform.CircleCropTransformation
import com.airbnb.lottie.LottieCompositionFactory
import com.airbnb.lottie.LottieDrawable
import com.kdz.jarvis.R
import com.kdz.jarvis.network.models.Thumbnail
import com.kdz.jarvis.repositories.result.Resource
import com.kdz.jarvis.ui.common.LoadingView
import com.kdz.jarvis.ui.common.ResourceStateViewContainer
import com.kdz.jarvis.ui.common.SimpleTextListView
import com.kdz.jarvis.ui.device.getUrl

@BindingAdapter("resource")
fun setResource(container: ResourceStateViewContainer, resource: Resource<Any>) {
    container.resource = resource
}

@BindingAdapter("resource")
fun setLoading(loadingView: LoadingView, resource: Resource<Any>) {
    loadingView.isGone = resource !is Resource.Loading
}

@BindingAdapter("thumbnail", "cropToCircle", requireAll = false)
fun setThumbnail(imageView: ImageView, thumbnail: Thumbnail, cropToCircle: Boolean) {

    val url = thumbnail.getUrl(imageView.context, Thumbnail.Variant.STANDARD)

    val drawable = LottieDrawable()
    LottieCompositionFactory.fromRawRes(imageView.context, R.raw.lottie_anim_loader).addListener { composition ->
        drawable.composition = composition

        imageView.load(url) {
            placeholder(drawable)
            crossfade(true)
            if(cropToCircle) {
                transformations(CircleCropTransformation())
            }
        }
    }
}

@BindingAdapter("texts")
fun setTexts(textListView: SimpleTextListView, texts: Resource<List<String>>?) {
    if(texts !is Resource.Success || texts.value.isNullOrEmpty()) return

    textListView.texts = texts.value
}