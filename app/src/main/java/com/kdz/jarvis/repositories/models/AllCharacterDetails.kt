package com.kdz.jarvis.repositories.models

import com.kdz.jarvis.network.models.ComicDetails
import com.kdz.jarvis.network.models.EventDetails
import com.kdz.jarvis.network.models.SeriesDetails
import com.kdz.jarvis.network.models.StoryDetails
import com.kdz.jarvis.repositories.result.Resource

data class AllCharacterDetails(
    val comics: Resource<List<ComicDetails>>,
    val events: Resource<List<EventDetails>>,
    val stories: Resource<List<StoryDetails>>,
    val series: Resource<List<SeriesDetails>>
)