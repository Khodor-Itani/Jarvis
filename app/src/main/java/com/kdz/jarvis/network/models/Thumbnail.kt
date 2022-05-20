package com.kdz.jarvis.network.models

data class Thumbnail(
    val extension: String,
    val path: String
) {

    fun getFullUrl(variant: Variant, size: Size) =
        "$path/${variant.name.lowercase()}_${size.name.lowercase()}.$extension"

    fun getAvailableSizes(variant: Variant) = when (variant) {
        Variant.PORTRAIT -> listOf(
            Size.SMALL,
            Size.MEDIUM,
            Size.XLARGE,
            Size.FANTASTIC,
            Size.UNCANNY,
            Size.INCREDIBLE
        )
        Variant.STANDARD -> listOf(
            Size.SMALL,
            Size.MEDIUM,
            Size.LARGE,
            Size.XLARGE,
            Size.FANTASTIC,
            Size.AMAZING
        )
        Variant.LANDSCAPE -> listOf(
            Size.SMALL,
            Size.MEDIUM,
            Size.LARGE,
            Size.XLARGE,
            Size.AMAZING,
            Size.INCREDIBLE
        )
    }

    enum class Variant {
        PORTRAIT, STANDARD, LANDSCAPE
    }

    enum class Size {
        SMALL, MEDIUM, LARGE, XLARGE, FANTASTIC, UNCANNY, AMAZING, INCREDIBLE
    }
}