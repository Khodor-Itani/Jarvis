package com.kdz.jarvis.network.services

import com.kdz.jarvis.network.models.ComicDetails
import com.kdz.jarvis.network.models.EventDetails
import com.kdz.jarvis.network.models.MarvelCharacter
import com.kdz.jarvis.network.models.SeriesDetails
import com.kdz.jarvis.network.models.StoryDetails
import com.kdz.jarvis.network.models.wrappers.BaseDataWrapper
import com.kdz.jarvis.network.result.NetworkResult
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

private const val DEFAULT_CHARACTER_LIMIT = 20
private const val DEFAULT_COMIC_LIMIT = 3
private const val DEFAULT_EVENT_LIMIT = 3
private const val DEFAULT_STORY_LIMIT = 3
private const val DEFAULT_SERIES_LIMIT = 3

interface MarvelService {

    @GET("characters")
    suspend fun getCharacters(
        @Query("limit") limit: Int = DEFAULT_CHARACTER_LIMIT,
        @Query("offset") offset: Int = 0
    ): NetworkResult<BaseDataWrapper<MarvelCharacter>>

    @GET("characters/{characterId}")
    suspend fun getCharacter(
        @Path("characterId") characterId: Int
    ): NetworkResult<BaseDataWrapper<MarvelCharacter>>

    @GET("characters/{characterId}/comics")
    suspend fun getComics(
        @Path("characterId") characterId: Int,
        @Query("limit") limit: Int = DEFAULT_COMIC_LIMIT
    ): NetworkResult<BaseDataWrapper<ComicDetails>>

    @GET("characters/{characterId}/events")
    suspend fun getEvents(
        @Path("characterId") characterId: Int,
        @Query("limit") limit: Int = DEFAULT_EVENT_LIMIT
    ): NetworkResult<BaseDataWrapper<EventDetails>>

    @GET("characters/{characterId}/stories")
    suspend fun getStories(
        @Path("characterId") characterId: Int,
        @Query("limit") limit: Int = DEFAULT_STORY_LIMIT
    ): NetworkResult<BaseDataWrapper<StoryDetails>>

    @GET("characters/{characterId}/series")
    suspend fun getSeries(
        @Path("characterId") characterId: Int,
        @Query("limit") limit: Int = DEFAULT_SERIES_LIMIT
    ): NetworkResult<BaseDataWrapper<SeriesDetails>>

    companion object {
        const val BASE_URL = "https://gateway.marvel.com/v1/public/"
    }
}