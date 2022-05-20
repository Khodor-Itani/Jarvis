package com.kdz.jarvis.network.services

import com.kdz.jarvis.network.models.Character
import com.kdz.jarvis.network.models.Error
import com.kdz.jarvis.network.models.containers.BaseDataContainer
import com.kdz.jarvis.network.models.wrappers.BaseDataWrapper
import com.kdz.jarvis.network.result.NetworkResult
import retrofit2.http.GET
import retrofit2.http.Query

interface MarvelService {

    @GET("characters/")
    suspend fun getCharacters(
        @Query("limit") limit: Int = 1
    ): NetworkResult<BaseDataWrapper<Character>>

    @GET("characters/{characterId}")
    suspend fun getCharacter(characterId: String) : NetworkResult<BaseDataWrapper<Character>>

    @GET("characters/{characterId}/comics")
    suspend fun getComics(characterId: String)

    @GET("characters/{characterId}/events")
    suspend fun getEvents(characterId: String)

    @GET("characters/{characterId}/series")
    suspend fun getSeries(characterId: String)

    @GET("characters/{characterId}/stories")
    suspend fun getStories(characterId: String)

    companion object {
        const val BASE_URL = "https://gateway.marvel.com/v1/public/"
    }
}