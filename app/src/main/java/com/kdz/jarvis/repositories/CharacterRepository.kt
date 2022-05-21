package com.kdz.jarvis.repositories

import com.kdz.jarvis.network.services.MarvelService
import com.kdz.jarvis.paging.CharacterPagingSource
import com.kdz.jarvis.repositories.extensions.mapToResource
import com.kdz.jarvis.repositories.models.AllCharacterDetails
import com.kdz.jarvis.repositories.result.Resource
import javax.inject.Inject
import javax.inject.Singleton
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

@Singleton
class CharacterRepository
@Inject
constructor(
    private val marvelService: MarvelService
) {

    fun newCharacterPagingSource() = CharacterPagingSource(marvelService)

    @OptIn(ExperimentalCoroutinesApi::class)
    fun getCharacterDetails(characterId: Int) = flow {
        emit(Resource.Loading)

        val allCharacterDetails = coroutineScope {
            val deferredComics = async { marvelService.getComics(characterId) }
            val deferredEvents = async { marvelService.getEvents(characterId) }
            val deferredStories = async { marvelService.getStories(characterId) }
            val deferredSeries = async { marvelService.getSeries(characterId) }

            awaitAll(deferredComics, deferredEvents, deferredStories, deferredSeries)

            val comics = deferredComics.getCompleted().mapToResource()
            val events = deferredEvents.getCompleted().mapToResource()
            val stories = deferredStories.getCompleted().mapToResource()
            val series = deferredSeries.getCompleted().mapToResource()

            AllCharacterDetails(comics, events, stories, series)
        }

        emit(Resource.Success(allCharacterDetails))
    }.flowOn(Dispatchers.IO)

}