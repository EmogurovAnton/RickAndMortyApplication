package com.example.rickandmortyapplication.domain.entities

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Episode(
    val id: Int,
    val name: String,
    val episode: String,
    val air_date: String
) : Parcelable
