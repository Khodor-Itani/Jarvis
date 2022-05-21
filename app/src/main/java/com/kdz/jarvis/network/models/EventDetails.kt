package com.kdz.jarvis.network.models


data class EventDetails(
    val characters: Characters,
    val comics: Comics,
    val creators: Creators,
    val description: String,
    val end: String,
    val id: String,
    val modified: String,
    val next: EventSummary,
    val previous: EventSummary,
    val resourceURI: String,
    val series: Series,
    val start: String,
    val stories: Stories,
    val thumbnail: Thumbnail,
    val title: String,
    val urls: List<Url>
)