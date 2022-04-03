package com.simplecalculator

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Weight(
    val weight: Double,
    val muscle: Double,
    val water: Double,
    val date: String,
    val fat: Double
): Parcelable