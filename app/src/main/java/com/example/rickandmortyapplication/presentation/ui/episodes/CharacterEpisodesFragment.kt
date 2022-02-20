package com.example.rickandmortyapplication.presentation.ui.episodes

import android.os.Bundle
import android.view.View
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.example.rickandmortyapplication.R
import com.example.rickandmortyapplication.databinding.FragmentCharacterEpisodesBinding
import com.example.rickandmortyapplication.util.EpisodesViewModelAssistedFactory
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class CharacterEpisodesFragment : Fragment(R.layout.fragment_character_episodes) {

    @Inject
    lateinit var assistedFactory: EpisodesViewModelAssistedFactory

    private val args by navArgs<CharacterEpisodesFragmentArgs>()
    private val viewModel by viewModels<CharacterEpisodesViewModel> {
        CharacterEpisodesViewModel.Factory(
            assistedFactory = assistedFactory,
            character = args.character
        )
    }

    private lateinit var binding: FragmentCharacterEpisodesBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentCharacterEpisodesBinding.bind(view)

        val adapter = CharacterEpisodesAdapter()

        with(binding) {
            characterEpisodesRecyclerView.apply {
                this.adapter = adapter
                setHasFixedSize(true)
            }

            viewModel.isLoading.observe(viewLifecycleOwner, { state ->
                onShowProgressBar(state)
            })

            viewModel.isError.observe(viewLifecycleOwner, { state ->
                onShowError(state)
            })

            charactersButtonRetry.setOnClickListener {
                viewModel.onRetryClicked()
            }
        }

        viewModel.episodes.observe(viewLifecycleOwner, {
            adapter.differ.submitList(it)
        })
    }

    private fun onShowError(errorState: Boolean) {
        with(binding) {
            charactersTextNoResults.isVisible = errorState
            charactersButtonRetry.isVisible = errorState
        }
    }

    private fun onShowProgressBar(progressBarState: Boolean) {
        binding.characterEpisodesProgressBar.isGone = progressBarState
    }
}