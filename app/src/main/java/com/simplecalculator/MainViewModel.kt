package com.simplecalculator

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.*
import com.simplecalculator.storage.Constants
import com.simplecalculator.storage.LocalStorage
import java.math.RoundingMode
import java.text.DecimalFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class MainViewModel() : ViewModel() {

    var date by mutableStateOf("")
    var weight by mutableStateOf("")
    var fatPercentage by mutableStateOf("")
    var musclePercentage by mutableStateOf("")
    var waterPercentage by mutableStateOf("")

    var fatWeight by mutableStateOf("")
    var muscleWeight by mutableStateOf("")
    var waterWeight by mutableStateOf("")
    var weights by mutableStateOf(ArrayList<Weight>())

    private val df = DecimalFormat("#,###.##")
    lateinit var prefs: LocalStorage

    init {
        df.roundingMode = RoundingMode.CEILING
    }

    fun fetchWeights(): ArrayList<Weight> {
       prefs.getList(Constants.LOCAL_STORAGE_WEIGHTS)?.let {
           weights = it
       }
       return weights
    }

    fun insertWeight() {
        prefs.storeToList(
            Constants.LOCAL_STORAGE_WEIGHTS,
            Weight(
                muscle = weightNonZero() * musclePercentage.toDouble() / 100,
                water = weightNonZero() * waterPercentage.toDouble() / 100,
                fat = weightNonZero() * fatPercentage.toDouble() / 100,
                weight = weightNonZero(),
                date = date
            )
        )
        fetchWeights()
    }

    fun calculateFat() {
        fatWeight =
            if (fatPercentage != "") df.format(weightNonZero() * fatPercentage.toDouble() / 100) else ""
    }

    fun calculateMuscle() {
        muscleWeight =
            if (musclePercentage != "") df.format(weightNonZero() * musclePercentage.toDouble() / 100) else ""
    }

    fun calculateWater() {
        waterWeight =
            if (waterPercentage != "") df.format(weightNonZero() * waterPercentage.toDouble() / 100) else ""
    }

    private fun weightNonZero() = if (weight != "") weight.toDouble() else 1.0
}