package com.kdz.jarvis.repositories

import androidx.paging.PagingSource
import com.kdz.jarvis.network.models.MarvelCharacter
import com.kdz.jarvis.network.services.MarvelService
import com.kdz.jarvis.paging.CharacterPagingSource
import com.kdz.jarvis.repositories.extensions.mapToRepositoryResult
import com.kdz.jarvis.repositories.result.Resource
import javax.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class CharacterRepository
@Inject
constructor(
    private val marvelService: MarvelService
) {

    fun characterPagingSource() = CharacterPagingSource(marvelService)

}