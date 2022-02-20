package com.example.rickandmortyapplication.data.network

import com.example.rickandmortyapplication.data.network.responses.CharacterResponse
import com.example.rickandmortyapplication.domain.entities.Episode
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface RickAndMortyApi {
    companion object {
        const val BASE_URL = "https://rickandmortyapi.com/api/"
    }

    @GET("character")
    suspend fun getCharacters(
        @Query("page") page: Int,
        @Query("count") count: Int
    ): Response<CharacterResponse>

    @GET("episode/{episodes}")
    suspend fun getEpisodes(
        @Path("episodes") episodes: String
    ): Response<List<Episode>>

    @GET("episode/{episode}")
    suspend fun getSingleEpisode(
        @Path("episode") episode: String
    ): Response<Episode>
}
