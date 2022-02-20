package com.example.rickandmortyapplication.util

import com.example.rickandmortyapplication.domain.entities.Character
import com.example.rickandmortyapplication.presentation.ui.episodes.CharacterEpisodesViewModel
import dagger.assisted.AssistedFactory

@AssistedFactory
interface EpisodesViewModelAssistedFactory {
    fun create(character: Character): CharacterEpisodesViewModel
}