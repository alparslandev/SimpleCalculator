package com.simplecalculator

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.*
import com.simplecalculator.storage.Constants
import com.simplecalculator.storage.LocalStorage
import java.math.RoundingMode
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.util.*

class MainViewModel() : ViewModel() {

    var weight by mutableStateOf("")
    var fatPercentage by mutableStateOf("")
    var musclePercentage by mutableStateOf("")
    var waterPercentage by mutableStateOf("")

    var fatWeight by mutableStateOf("")
    var muscleWeight by mutableStateOf("")
    var waterWeight by mutableStateOf("")
    private val weights = MutableLiveData<List<Weight>>()

    private val df = DecimalFormat("#,###.##")
    lateinit var prefs: LocalStorage

    init {
        df.roundingMode = RoundingMode.CEILING
    }

    fun fetchWeights() = prefs.getList<List<Weight>>(Constants.LOCAL_STORAGE_WEIGHTS)

    fun insertWeight() {
        prefs.storeToList(
            Constants.LOCAL_STORAGE_WEIGHTS,
            Weight(
                muscle = weightNonZero() * musclePercentage.toDoubleEx() / 100,
                water = weightNonZero() * waterPercentage.toDoubleEx() / 100,
                fat = weightNonZero() * fatPercentage.toDoubleEx() / 100,
                weight = weightNonZero(),
                date = Date().toString()
            )
        )
    }

    fun calculateFat() {
        fatWeight =
            if (fatPercentage != "") df.format(weightNonZero() * fatPercentage.toDoubleEx() / 100) else ""
    }

    fun calculateMuscle() {
        muscleWeight =
            if (musclePercentage != "") df.format(weightNonZero() * musclePercentage.toDoubleEx() / 100) else ""
    }

    fun calculateWater() {
        waterWeight =
            if (waterPercentage != "") df.format(weightNonZero() * waterPercentage.toDoubleEx() / 100) else ""
    }

    private fun weightNonZero() = if (weight != "") weight.toDoubleEx() else 1.0
}

fun String.toDoubleEx() : Double {
    val decimalSymbol = DecimalFormatSymbols.getInstance().decimalSeparator
    return if (decimalSymbol == ',') {
        this.replace(decimalSymbol, '.').toDouble()
    } else {
        this.toDouble()
    }
}