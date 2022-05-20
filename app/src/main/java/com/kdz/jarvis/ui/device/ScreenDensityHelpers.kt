package com.kdz.jarvis.ui.device

import android.content.Context
import android.util.DisplayMetrics
import com.kdz.jarvis.network.models.Thumbnail
import kotlin.math.min

enum class ScreenDensity {
    LDPI, MDPI, HDPI, XHDPI, XXHDPI, XXXHDPI
}

val DisplayMetrics.screenDensityClass
    get() = when (density) {
        in (0F..0.75F) -> ScreenDensity.LDPI
        in (0.75F..1F) -> ScreenDensity.MDPI
        in (1F..1.5F) -> ScreenDensity.HDPI
        in (1.5F..2F) -> ScreenDensity.XHDPI
        in (2F..3F) -> ScreenDensity.XXHDPI
        in (1.5F..2F) -> ScreenDensity.XXHDPI
        in (2F..3F) -> ScreenDensity.XXHDPI
        else -> ScreenDensity.XXXHDPI
    }

fun Thumbnail.getUrl(context: Context, variant: Thumbnail.Variant): String {
    val screenDensityClassOrdinal = context.resources.displayMetrics.screenDensityClass.ordinal
    val sizes = getAvailableSizes(variant)
    val sizeIndex = min(screenDensityClassOrdinal, sizes.size - 1)

    val selectedScreenSize = sizes[sizeIndex]

    return getFullUrl(variant, selectedScreenSize)
}