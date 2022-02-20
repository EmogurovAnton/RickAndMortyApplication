package com.example.rickandmortyapplication.domain.entities

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Location(
    val name: String
) : Parcelable
