package com.example.rickandmortyapplication.presentation.ui.details

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.View
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.example.rickandmortyapplication.R
import com.example.rickandmortyapplication.databinding.FragmentCharacterDetailsBinding

class CharacterDetailsFragment : Fragment(R.layout.fragment_character_details) {
    private lateinit var binding: FragmentCharacterDetailsBinding
    private val args by navArgs<CharacterDetailsFragmentArgs>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentCharacterDetailsBinding.bind(view)

        val character = args.character

        with(binding) {
            Glide.with(this@CharacterDetailsFragment)
                .load(character.image)
                .error(R.drawable.ic_error)
                .circleCrop()
                .listener(loadingListener)
                .into(characterDetailsImageCharacter)

            characterDetailsTextCharacterLocation.text = character.location.name
            characterDetailsTextCharacterSpecies.text = character.species
            characterDetailsTextCharacterStatus.text = character.correctStatus

            characterDetailsButtonEpisodes.setOnClickListener {
                val action = CharacterDetailsFragmentDirections
                    .actionCharacterDetailsFragmentToCharacterEpisodesFragment(character)
                findNavController().navigate(action)
            }
        }
    }

    private val loadingListener = object : RequestListener<Drawable> {
        override fun onLoadFailed(
            e: GlideException?,
            model: Any?,
            target: Target<Drawable>?,
            isFirstResource: Boolean
        ): Boolean {
            with(binding) {
                charactersDetailsProgressBar.isGone = true
                characterDetailsTextNoResults.isVisible = true

                characterDetailsTextLocation.isVisible = false
                characterDetailsTextCharacterLocation.isVisible = false

                characterDetailsTextSpecies.isVisible = false
                characterDetailsTextCharacterSpecies.isVisible = false

                characterDetailsTextStatus.isVisible = false
                characterDetailsTextCharacterStatus.isVisible = false

                characterDetailsButtonEpisodes.isVisible = false
            }
            return false
        }

        override fun onResourceReady(
            resource: Drawable?,
            model: Any?,
            target: Target<Drawable>?,
            dataSource: DataSource?,
            isFirstResource: Boolean
        ): Boolean {
            with(binding) {
                charactersDetailsProgressBar.isGone = true

                characterDetailsTextLocation.isVisible = true
                characterDetailsTextCharacterLocation.isVisible = true

                characterDetailsTextSpecies.isVisible = true
                characterDetailsTextCharacterSpecies.isVisible = true

                characterDetailsTextStatus.isVisible = true
                characterDetailsTextCharacterStatus.isVisible = true

                characterDetailsButtonEpisodes.isVisible = true
            }
            return false
        }
    }
}