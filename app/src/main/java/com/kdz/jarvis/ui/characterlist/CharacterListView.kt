package com.kdz.jarvis.ui.characterlist

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.kdz.jarvis.databinding.CellCharacterBinding
import com.kdz.jarvis.network.models.MarvelCharacter

class CharacterListView
@JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : RecyclerView(context, attrs, defStyleAttr) {

    var marvelCharacters: List<MarvelCharacter>?
        get() = (adapter as? CharacterAdapter)?.currentList
        set(value) {
            (adapter as? CharacterAdapter)?.submitList(value)
        }

    init {
        adapter = CharacterAdapter()
    }
}

private class CharacterAdapter :
    ListAdapter<MarvelCharacter, CharacterViewHolder>(CharacterDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharacterViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = CellCharacterBinding.inflate(layoutInflater)
        return CharacterViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CharacterViewHolder, position: Int) =
        with(holder.binding) {
            val character = getItem(position) ?: return@with
        }
}

private class CharacterViewHolder(
    val binding: CellCharacterBinding
) : RecyclerView.ViewHolder(binding.root)

private class CharacterDiffCallback : DiffUtil.ItemCallback<MarvelCharacter>() {
    override fun areItemsTheSame(oldItem: MarvelCharacter, newItem: MarvelCharacter) = oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: MarvelCharacter, newItem: MarvelCharacter): Boolean {
        return areItemsTheSame(oldItem, newItem)
    }
}
