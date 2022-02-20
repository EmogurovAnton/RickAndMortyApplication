package com.example.rickandmortyapplication.presentation.ui.characters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.example.rickandmortyapplication.R
import com.example.rickandmortyapplication.databinding.ItemCharacterBinding
import com.example.rickandmortyapplication.domain.entities.Character

class CharactersAdapter(
    private val onCharacterClickListener: (Character) -> Unit
) : PagingDataAdapter<Character, CharactersAdapter.CharactersViewHolder>(CharacterComparator()) {

    companion object{
        private const val ALIVE = "alive"
        private const val UNKNOWN = "unknown"
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharactersViewHolder {
        val binding = ItemCharacterBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )

        return CharactersViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CharactersViewHolder, position: Int) {
        getItem(position)?.let { currentItem -> holder.bind(currentItem) }
    }

    inner class CharactersViewHolder(private val binding: ItemCharacterBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.setOnClickListener {
                val position = bindingAdapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    getItem(position)?.let { character ->
                        onCharacterClickListener.invoke(character)
                    }
                }
            }
        }

        fun bind(character: Character) {
            with(binding) {
                Glide.with(charactersImageCharacter)
                    .load(character.image)
                    .circleCrop()
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .error(R.drawable.ic_error)
                    .into(charactersImageCharacter)

                charactersTextCharacterName.text = character.name
                when (character.status.lowercase()) {
                    ALIVE -> charactersImageStatus.setImageResource(R.drawable.ic_alive)
                    UNKNOWN -> charactersImageStatus.setImageResource(R.drawable.ic_unknown)
                    else -> charactersImageStatus.setImageResource(R.drawable.ic_dead)
                }
                charactersTextStatus.text = character.correctStatus
            }
        }
    }

    private class CharacterComparator : DiffUtil.ItemCallback<Character>() {
        override fun areItemsTheSame(oldItem: Character, newItem: Character) =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: Character, newItem: Character) =
            oldItem == newItem
    }
}