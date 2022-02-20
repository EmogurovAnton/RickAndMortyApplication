package com.example.rickandmortyapplication.presentation.ui.episodes

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.rickandmortyapplication.R
import com.example.rickandmortyapplication.databinding.ItemEpisodeBinding
import com.example.rickandmortyapplication.domain.entities.Episode

class CharacterEpisodesAdapter
    : RecyclerView.Adapter<CharacterEpisodesAdapter.CharacterEpisodesViewHolder>() {

    companion object {
        private const val EPISODE_START_INDEX = 0
        private const val EPISODE_LAST_INDEX = 3
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharacterEpisodesViewHolder {
        val binding = ItemEpisodeBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )

        return CharacterEpisodesViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CharacterEpisodesViewHolder, position: Int) {
        val currentEpisode = differ.currentList[position]

        holder.bind(currentEpisode)
    }

    override fun getItemCount(): Int = differ.currentList.size

    private val diffCallback = object : DiffUtil.ItemCallback<Episode>() {
        override fun areItemsTheSame(oldItem: Episode, newItem: Episode) =
            oldItem.episode == newItem.episode

        override fun areContentsTheSame(oldItem: Episode, newItem: Episode) =
            oldItem == newItem
    }

    val differ = AsyncListDiffer(this, diffCallback)

    class CharacterEpisodesViewHolder(private val binding: ItemEpisodeBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(episode: Episode) {
            val episodeImage = getEpisodeImage(episode)

            with(binding) {
                Glide.with(charactersEpisodeImageEpisode)
                    .load(episodeImage)
                    .centerCrop()
                    .into(charactersEpisodeImageEpisode)

                characterEpisodesTextEpisodeName.text = episode.name
                characterEpisodesTextAirDate.text = episode.air_date
            }
        }

        private fun getEpisodeImage(episode: Episode): Int {
            val currentEpisodeId = episode.episode
                .substring(
                    EPISODE_START_INDEX,
                    EPISODE_LAST_INDEX
                )

            return when (currentEpisodeId) {
                this.itemView.context.getString(R.string.season_1) -> R.drawable.season_1
                this.itemView.context.getString(R.string.season_2) -> R.drawable.season_2
                this.itemView.context.getString(R.string.season_3) -> R.drawable.season_3
                this.itemView.context.getString(R.string.season_4) -> R.drawable.season_4
                else -> R.drawable.season_5
            }
        }
    }
}