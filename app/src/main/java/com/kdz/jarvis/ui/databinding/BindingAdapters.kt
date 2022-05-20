package com.kdz.jarvis.ui.databinding

import androidx.databinding.BindingAdapter
import com.kdz.jarvis.network.models.MarvelCharacter
import com.kdz.jarvis.ui.characterlist.CharacterListView

@BindingAdapter("marvelCharacters")
fun setList(characterListView: CharacterListView, marvelCharacters: List<MarvelCharacter>) {
    characterListView.marvelCharacters = marvelCharacters
}