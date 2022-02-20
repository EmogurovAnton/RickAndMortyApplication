package com.example.rickandmortyapplication.presentation.ui.characters

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import com.example.rickandmortyapplication.R
import com.example.rickandmortyapplication.databinding.FragmentCharactersBinding
import com.example.rickandmortyapplication.util.DividerItemDecoration
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CharactersFragment : Fragment(R.layout.fragment_characters) {
    private lateinit var binding: FragmentCharactersBinding
    private val viewModel by viewModels<CharactersViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentCharactersBinding.bind(view)

        val itemDecoration = DividerItemDecoration(
            requireContext(),
            R.drawable.characters_line_devider
        )

        val adapter = CharactersAdapter { character ->
            val action = CharactersFragmentDirections
                .actionCharactersFragmentToCharacterDetailsFragment(
                    character = character,
                    characterName = character.name
                )
            findNavController().navigate(action)
        }

        with(binding) {
            charactersRecyclerView.apply {
                this.adapter = adapter.withLoadStateHeaderAndFooter(
                    header = CharactersLoadStateAdapter { adapter.retry() },
                    footer = CharactersLoadStateAdapter { adapter.retry() }
                )
                addItemDecoration(itemDecoration)
                setHasFixedSize(true)
            }

            charactersButtonRetry.setOnClickListener { adapter.retry() }

            adapter.addLoadStateListener { loadState ->
                charactersProgressBar.isVisible = loadState.source.refresh is LoadState.Loading
                charactersRecyclerView.isVisible = loadState.source.refresh is LoadState.NotLoading
                charactersTextNoResults.isVisible = loadState.source.refresh is LoadState.Error
                charactersButtonRetry.isVisible = loadState.source.refresh is LoadState.Error
            }
        }

        viewModel.characters.observe(viewLifecycleOwner) { characterList ->
            adapter.submitData(viewLifecycleOwner.lifecycle, characterList)
        }
    }
}