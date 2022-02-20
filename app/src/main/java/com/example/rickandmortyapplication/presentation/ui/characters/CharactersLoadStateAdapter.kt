package com.example.rickandmortyapplication.presentation.ui.characters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.rickandmortyapplication.databinding.LoadStateBarBinding

class CharactersLoadStateAdapter(
    private val onLoadRetryListener: () -> Unit
) : LoadStateAdapter<CharactersLoadStateAdapter.CharactersLoadStateViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        loadState: LoadState
    ): CharactersLoadStateViewHolder {
        val binding = LoadStateBarBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return CharactersLoadStateViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CharactersLoadStateViewHolder, loadState: LoadState) {
        holder.bind(loadState)
    }

    inner class CharactersLoadStateViewHolder(private val binding: LoadStateBarBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.loadStateButtonRetry.setOnClickListener {
                onLoadRetryListener.invoke()
            }
        }

        fun bind(loadState: LoadState) {
            with(binding) {
                loadStateProgressBar.isVisible = loadState is LoadState.Loading
                loadStateTextNoResults.isVisible = loadState !is LoadState.Loading
                loadStateButtonRetry.isVisible = loadState !is LoadState.Loading
            }
        }
    }
}