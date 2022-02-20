package com.example.rickandmortyapplication.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.rickandmortyapplication.data.network.RickAndMortyApi
import com.example.rickandmortyapplication.domain.entities.Character
import retrofit2.HttpException
import java.io.IOException

class CharactersPagingSource(
    private val rickAndMortyApi: RickAndMortyApi
) : PagingSource<Int, Character>() {

    companion object {
        private const val CHARACTERS_STARTING_PAGE = 1
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Character> {
        val page = params.key ?: CHARACTERS_STARTING_PAGE
        val pageSize = params.loadSize

        return try {
            val response = rickAndMortyApi.getCharacters(page, pageSize)
            if (response.isSuccessful) {
                val characters = response.body()?.results ?: emptyList()

                val prevKey = if (page == CHARACTERS_STARTING_PAGE) null else page - 1
                val nextKey = if (characters.isEmpty()) null else page + 1

                LoadResult.Page(data = characters, prevKey = prevKey, nextKey = nextKey)
            } else {
                LoadResult.Page(data = emptyList(), nextKey = null, prevKey = null)
            }
        } catch (exception: IOException) {
            LoadResult.Error(exception)
        } catch (exception: HttpException) {
            LoadResult.Error(exception)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Character>): Int? {
        TODO("Не подразумевается манипуляция с данными в списке")
    }
}



