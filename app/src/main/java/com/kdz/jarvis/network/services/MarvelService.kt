package com.kdz.jarvis.network.services

import com.kdz.jarvis.network.models.MarvelCharacter
import com.kdz.jarvis.network.models.wrappers.BaseDataWrapper
import com.kdz.jarvis.network.result.NetworkResult
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MarvelService {

    @GET("characters")
    suspend fun getCharacters(
        @Query("limit") limit: Int = 20
    ): NetworkResult<BaseDataWrapper<MarvelCharacter>>

    @GET("characters/{characterId}")
    suspend fun getCharacter(
        @Path("characterId") characterId: String
    ): NetworkResult<BaseDataWrapper<MarvelCharacter>>

    @GET("characters/{characterId}/comics")
    suspend fun getComics(
        @Path("characterId") characterId: String
    )

    @GET("characters/{characterId}/events")
    suspend fun getEvents(
        @Path("characterId") characterId: String
    )

    @GET("characters/{characterId}/series")
    suspend fun getSeries(
        @Path("characterId") characterId: String
    )

    @GET("characters/{characterId}/stories")
    suspend fun getStories(
        @Path("characterId") characterId: String
    )

    companion object {
        const val BASE_URL = "https://gateway.marvel.com/v1/public/"
    }
}