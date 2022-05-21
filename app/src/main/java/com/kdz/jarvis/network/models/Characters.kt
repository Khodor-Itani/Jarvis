package com.kdz.jarvis.network.models

data class Characters(
    val available: String,
    val collectionURI: String,
    val items: List<CharacterRole>,
    val returned: String
)