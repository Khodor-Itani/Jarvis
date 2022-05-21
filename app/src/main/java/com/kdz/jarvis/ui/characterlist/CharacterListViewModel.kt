package com.kdz.jarvis.ui.characterlist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.kdz.jarvis.network.models.MarvelCharacter
import com.kdz.jarvis.repositories.CharacterRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow

private const val CHARACTERS_PER_PAGE = 20

@HiltViewModel
class CharacterListViewModel
@Inject
constructor(
    private val characterRepository: CharacterRepository
) : ViewModel() {

    val characters: Flow<PagingData<MarvelCharacter>> = Pager(
        config = PagingConfig(pageSize = CHARACTERS_PER_PAGE, enablePlaceholders = false),
        pagingSourceFactory = { characterRepository.newCharacterPagingSource() }
    ).flow.cachedIn(viewModelScope)

}