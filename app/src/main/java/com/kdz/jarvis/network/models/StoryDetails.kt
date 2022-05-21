package com.kdz.jarvis.network.models

data class StoryDetails(
    val characters: Characters,
    val comics: Comics,
    val creators: Creators,
    val description: String,
    val events: Events,
    val id: String,
    val modified: String,
    val originalissue: IssueSummary,
    val resourceURI: String,
    val series: Series,
    val thumbnail: Thumbnail,
    val title: String,
    val type: String
)