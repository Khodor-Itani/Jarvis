package com.kdz.jarvis.network.models

data class Comics(
    val available: String,
    val collectionURI: String,
    val items: List<ComicSummary>,
    val returned: String
)