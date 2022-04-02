package com.simplecalculator

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import java.math.RoundingMode
import java.text.DecimalFormat

class MainViewModel : ViewModel() {

    var weight by mutableStateOf("")
    var fatPercentage by mutableStateOf("")
    var musclePercentage by mutableStateOf("")
    var waterPercentage by mutableStateOf("")

    var fatWeight by mutableStateOf("")
    var muscleWeight by mutableStateOf("")
    var waterWeight by mutableStateOf("")

    private val df = DecimalFormat("#,###.##")

    init {
        df.roundingMode = RoundingMode.CEILING
    }

    fun calculateFat() {
        fatWeight = if (fatPercentage != "") df.format(weightNonZero() * fatPercentage.toDouble() / 100) else ""
    }

    fun calculateMuscle() {
        muscleWeight = if (musclePercentage != "") df.format(weightNonZero() * musclePercentage.toDouble() / 100) else ""
    }

    fun calculateWater() {
        waterWeight = if (waterPercentage != "") df.format(weightNonZero() * waterPercentage.toDouble() / 100) else ""
    }

    private fun weightNonZero() = if (weight != "") weight.toDouble() else 1.0
}