package com.example.rickandmortyapplication.data.repositories

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.liveData
import com.example.rickandmortyapplication.data.network.RickAndMortyApi
import com.example.rickandmortyapplication.data.paging.CharactersPagingSource
import javax.inject.Inject

class CharactersRepository @Inject constructor(
    private val rickAndMortyApi: RickAndMortyApi
) {
    fun getCharacters() =
        Pager(
            config = PagingConfig(
                pageSize = 20,
                maxSize = 100,
                enablePlaceholders = true
            ),
            pagingSourceFactory = {
                CharactersPagingSource(rickAndMortyApi)
            }
        ).liveData
}