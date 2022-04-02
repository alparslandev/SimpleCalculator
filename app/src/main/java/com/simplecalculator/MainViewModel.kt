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
        fatWeight = (weight.toDouble() * fatPercentage.toDouble() / 100).toString()
    }

    fun calculateMuscle() {
        muscleWeight = (weight.toDouble() * musclePercentage.toDouble() / 100).toString()
    }

    fun calculateWater() {
        waterWeight = (weight.toDouble() * waterPercentage.toDouble() / 100).toString()
    }

    private fun weightNonZero(): Double {
        fatWeight = ""
        muscleWeight = ""
        waterWeight = ""
        return if (weight != "") weight.toDouble() else 1.0
    }
}