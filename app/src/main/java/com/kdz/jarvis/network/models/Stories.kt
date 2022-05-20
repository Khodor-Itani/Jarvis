package com.kdz.jarvis.network.models

data class Stories(
    val available: String,
    val collectionURI: String,
    val items: List<StorySummary>,
    val returned: String
)