package com.example.rickandmortyapplication.domain.entities

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Character(
    val id: Int,
    val name: String,
    val status: String,
    val species: String,
    val gender: String,
    val location: Location,
    val image: String,
    val episode: List<String>
) : Parcelable {
    val correctStatus
        get() = if (status == "unknown") "Unknown" else status
}
