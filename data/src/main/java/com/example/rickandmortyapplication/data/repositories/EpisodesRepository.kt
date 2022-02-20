package com.example.rickandmortyapplication.data.repositories

import androidx.lifecycle.MutableLiveData
import com.example.rickandmortyapplication.data.network.RickAndMortyApi
import com.example.rickandmortyapplication.domain.entities.Character
import com.example.rickandmortyapplication.domain.entities.Episode
import retrofit2.HttpException
import java.io.IOException
import java.net.SocketTimeoutException
import javax.inject.Inject

class EpisodesRepository @Inject constructor(
    private val rickAndMortyApi: RickAndMortyApi
) {

    suspend fun getEpisodes(character: Character, episodes: MutableLiveData<List<Episode>>) {
        val episodesIds = getEpisodesIds(character)

        try {
            if (episodesIds.length > 3) {
                val response = rickAndMortyApi.getEpisodes(episodesIds)
                if (response.isSuccessful) {
                    episodes.value = response.body()
                }
            } else {
                val response = rickAndMortyApi.getSingleEpisode(episodesIds)
                if (response.isSuccessful) {
                    val singleEpisode = response.body() as Episode
                    val episodeList = listOf(singleEpisode)

                    episodes.value = episodeList
                }
            }
        } catch (exception: IOException) {
            exception.printStackTrace()
        } catch (exception: HttpException) {
            exception.printStackTrace()
        } catch (exception: SocketTimeoutException) {
            exception.printStackTrace()
        }
    }

    private fun getEpisodesIds(character: Character): String {
        val episodeUrl = character.episode
        var episodesIds = ""

        episodeUrl.forEach {
            val lastIndex = it.lastIndexOf("/")
            val episode = it.substring(lastIndex + 1, it.length)
            episodesIds += "$episode,"
        }

        val episodesIdLastIndex = episodesIds.length - 1
        val characterEpisodes = episodesIds.substring(0, episodesIdLastIndex)

        return characterEpisodes
    }
}