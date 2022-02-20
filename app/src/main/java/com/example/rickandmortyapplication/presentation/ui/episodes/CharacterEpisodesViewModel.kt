package com.example.rickandmortyapplication.presentation.ui.episodes

import androidx.lifecycle.*
import com.example.rickandmortyapplication.data.repositories.EpisodesRepository
import com.example.rickandmortyapplication.domain.entities.Episode
import com.example.rickandmortyapplication.domain.entities.Character
import com.example.rickandmortyapplication.util.EpisodesViewModelAssistedFactory
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.launch

class CharacterEpisodesViewModel @AssistedInject constructor(
    private val episodesRepository: EpisodesRepository,
    @Assisted private val character: Character
) : ViewModel() {

    private val _episodes: MutableLiveData<List<Episode>> = MutableLiveData()
    val episodes: LiveData<List<Episode>> = _episodes

    private val _isLoading: MutableLiveData<Boolean> = MutableLiveData()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _isError: MutableLiveData<Boolean> = MutableLiveData()
    val isError: LiveData<Boolean> = _isError


    init {
        getCharacterEpisodes()
    }

    fun onRetryClicked() {
        onRetry()
        getCharacterEpisodes()
    }

    private fun getCharacterEpisodes() = viewModelScope.launch {
        onLoading()
        episodesRepository.getEpisodes(character, _episodes)
        onLoadingFinished()
    }

    private fun onLoading() {
        _isLoading.value = false
    }

    private fun onLoadingFinished() {
        _isLoading.value = true

        _isError.value = episodes.value == null
    }

    private fun onRetry() {
        _isError.value = false
    }

    class Factory(
        private val assistedFactory: EpisodesViewModelAssistedFactory,
        private val character: Character
    ) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return assistedFactory.create(character) as T
        }
    }
}