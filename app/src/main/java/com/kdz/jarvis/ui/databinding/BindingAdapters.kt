package com.kdz.jarvis.ui.databinding

import android.util.Log
import android.widget.ImageView
import androidx.core.view.isGone
import androidx.databinding.BindingAdapter
import coil.load
import coil.transform.CircleCropTransformation
import com.airbnb.lottie.Lottie
import com.airbnb.lottie.LottieCompositionFactory
import com.airbnb.lottie.LottieDrawable
import com.kdz.jarvis.R
import com.kdz.jarvis.network.models.MarvelCharacter
import com.kdz.jarvis.network.models.Thumbnail
import com.kdz.jarvis.repositories.result.Resource
import com.kdz.jarvis.ui.characterlist.CharacterListView
import com.kdz.jarvis.ui.common.LoadingView
import com.kdz.jarvis.ui.device.getUrl
import com.kdz.jarvis.ui.device.screenDensityClass
import kotlin.math.min

@BindingAdapter("marvelCharacters")
fun setList(characterListView: CharacterListView, marvelCharacters: Resource<List<MarvelCharacter>>?) {
    if(marvelCharacters !is Resource.Success) return

    characterListView.marvelCharacters = marvelCharacters.value
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