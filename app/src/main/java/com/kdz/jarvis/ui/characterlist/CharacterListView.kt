package com.kdz.jarvis.ui.characterlist

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.kdz.jarvis.databinding.CellCharacterBinding
import com.kdz.jarvis.network.models.MarvelCharacter
import jp.wasabeef.recyclerview.animators.FadeInUpAnimator

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
        itemAnimator = FadeInUpAnimator()
        layoutManager = LinearLayoutManager(context)
        adapter = CharacterAdapter()
        addItemDecoration(DividerItemDecoration(context, VERTICAL))
    }
}

private class CharacterAdapter :
    ListAdapter<MarvelCharacter, CharacterViewHolder>(CharacterDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharacterViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = CellCharacterBinding.inflate(layoutInflater, parent, false)
        return CharacterViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CharacterViewHolder, position: Int) {
        holder.binding.marvelCharacter = getItem(position) ?: return
    }
}

private class CharacterViewHolder(
    val binding: CellCharacterBinding
) : RecyclerView.ViewHolder(binding.root)

private class CharacterDiffCallback : DiffUtil.ItemCallback<MarvelCharacter>() {
    override fun areItemsTheSame(oldItem: MarvelCharacter, newItem: MarvelCharacter): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: MarvelCharacter, newItem: MarvelCharacter): Boolean {
        return areItemsTheSame(oldItem, newItem)
    }
}
