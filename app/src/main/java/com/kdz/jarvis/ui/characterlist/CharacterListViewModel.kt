package com.kdz.jarvis.ui.characterlist

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.kdz.jarvis.network.models.MarvelCharacter
import com.kdz.jarvis.repositories.CharacterRepository
import com.kdz.jarvis.repositories.result.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onEach

private const val CHARACTERS_PER_PAGE = 20

@HiltViewModel
class CharacterListViewModel
@Inject
constructor(
    private val characterRepository: CharacterRepository
) : ViewModel() {

    val characters: Flow<PagingData<MarvelCharacter>> = Pager(
        config = PagingConfig(pageSize = CHARACTERS_PER_PAGE, enablePlaceholders = false),
        pagingSourceFactory = { characterRepository.characterPagingSource() }
    ).flow.cachedIn(viewModelScope)

}