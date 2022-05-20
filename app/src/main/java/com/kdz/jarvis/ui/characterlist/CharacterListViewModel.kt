package com.kdz.jarvis.ui.characterlist

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kdz.jarvis.network.models.MarvelCharacter
import com.kdz.jarvis.repositories.CharacterRepository
import com.kdz.jarvis.repositories.result.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onEach

@HiltViewModel
class CharacterListViewModel
@Inject
constructor(
    private val characterRepository: CharacterRepository
) : ViewModel() {

    private val _characters = MutableStateFlow<Resource<List<MarvelCharacter>>>(Resource.Loading)
    val characters: StateFlow<Resource<List<MarvelCharacter>>> = _characters

    init {
        fetchCharacters()
    }

    private fun fetchCharacters() = characterRepository.getCharacters().onEach(_characters::emit).launchIn(viewModelScope)
}