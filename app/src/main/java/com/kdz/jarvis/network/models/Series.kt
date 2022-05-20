package com.kdz.jarvis.network.models

data class Series(
    val available: String,
    val collectionURI: String,
    val items: List<SeriesSummary>,
    val returned: String
)