package com.kdz.jarvis.network.models

data class Stories(
    val available: String,
    val collectionURI: String,
    val items: List<StoriesSummary>,
    val returned: String
)