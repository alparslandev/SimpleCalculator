package com.simplecalculator

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class MainViewModel : ViewModel() {

    var weight by mutableStateOf("")
    var fatPercentage by mutableStateOf("")
    var musclePercentage by mutableStateOf("")
    var waterPercentage by mutableStateOf("")

    var fatWeight by mutableStateOf("")
    var muscleWeight by mutableStateOf("")
    var waterWeight by mutableStateOf("")

    fun calculateFat() {
        fatWeight = if (fatPercentage != "") (weightNonZero() * fatPercentage.toDouble() / 100).toString() else ""
    }

    fun calculateMuscle() {
        muscleWeight = if (musclePercentage != "") (weightNonZero() * musclePercentage.toDouble() / 100).toString() else ""
    }

    fun calculateWater() {
        waterWeight = if (waterPercentage != "") (weightNonZero() * waterPercentage.toDouble() / 100).toString() else ""
    }

    private fun weightNonZero() = if (weight != "") weight.toDouble() else 1.0
}