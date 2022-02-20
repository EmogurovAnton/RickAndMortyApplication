package com.example.rickandmortyapplication.data.network.responses

import com.example.rickandmortyapplication.domain.entities.Character

data class CharacterResponse(
    val results: List<Character>
)