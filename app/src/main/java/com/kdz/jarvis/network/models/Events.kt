package com.kdz.jarvis.network.models

data class Events(
    val available: String,
    val collectionURI: String,
    val items: List<EventSummary>,
    val returned: String
)