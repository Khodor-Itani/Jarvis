package com.kdz.jarvis.ui.characterdetails

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kdz.jarvis.repositories.CharacterRepository
import com.kdz.jarvis.repositories.result.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@HiltViewModel
class CharacterDetailsViewModel
@Inject
constructor(
    private val characterRepository: CharacterRepository
) : ViewModel() {

    var characterId: Int = 0

    private val _comics = MutableStateFlow<Resource<List<String>>>(Resource.Loading)
    val comics: StateFlow<Resource<List<String>>> = _comics

    private val _events = MutableStateFlow<Resource<List<String>>>(Resource.Loading)
    val events: StateFlow<Resource<List<String>>> = _events

    private val _stories = MutableStateFlow<Resource<List<String>>>(Resource.Loading)
    val stories: StateFlow<Resource<List<String>>> = _stories

    private val _series = MutableStateFlow<Resource<List<String>>>(Resource.Loading)
    val series: StateFlow<Resource<List<String>>> = _series

    fun requestCharacterDetails() {
        characterRepository.getCharacterDetails(characterId)
            .onEach { allCharacterDetails ->
                if (allCharacterDetails !is Resource.Success) return@onEach

                _comics.emit(allCharacterDetails.value.comics.map { it.map { it.title } })
                _events.emit(allCharacterDetails.value.events.map { it.map { it.title } })
                _stories.emit(allCharacterDetails.value.stories.map { it.map { it.title } })
                _series.emit(allCharacterDetails.value.series.map { it.map { it.title } })
            }.launchIn(viewModelScope)
    }

}