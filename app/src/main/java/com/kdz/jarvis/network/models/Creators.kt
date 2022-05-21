package com.kdz.jarvis.network.models

data class Creators(
    val available: String,
    val collectionURI: String,
    val items: List<CreatorSummary>,
    val returned: String
)