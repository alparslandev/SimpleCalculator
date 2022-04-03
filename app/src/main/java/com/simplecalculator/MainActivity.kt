package com.simplecalculator

import android.os.Bundle
import android.view.WindowManager
import android.widget.CalendarView
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import com.simplecalculator.storage.DefaultLocalStorageImpl
import com.simplecalculator.storage.LocalStorage
import com.simplecalculator.ui.theme.SimpleCalculatorTheme
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_MODE_CHANGED)
        val mainViewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        mainViewModel.prefs = DefaultLocalStorageImpl(this)
        setContent {
            val scrollState = rememberScrollState()
            MaterialTheme {
                Surface(color = MaterialTheme.colorScheme.background) {
                    SimpleCalculatorComponents(mainViewModel)
                }
            }
        }
    }
}

@Composable
fun SimpleCalculatorComponents(viewModel: MainViewModel) {
    val keyboardOptions = KeyboardOptions(
        imeAction = ImeAction.Next, keyboardType = KeyboardType.Number
    )
    val colors = TextFieldDefaults.textFieldColors(
        textColor = Color.Black, backgroundColor = Color.Transparent
    )
    Column(modifier = Modifier.padding(16.dp)) {
        AndroidView({ CalendarView(it) },
            modifier = Modifier.fillMaxWidth(),
            update = { views ->
                views.setOnDateChangeListener { calendarView, i, i2, i3 ->
                    viewModel.date =
                        LocalDate.of(i, (i2 + 1), i3).format(DateTimeFormatter.ofPattern("dd MMMM"))
                            .toString()
                }
            })
        Row(
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxWidth()
        ) {
            OutlinedTextField(value = viewModel.weight,
                modifier = Modifier.weight(1f),
                onValueChange = {
                    viewModel.weight = it
                },
                keyboardOptions = keyboardOptions,
                colors = colors,
                placeholder = { Text("Kilo") })

            Spacer(Modifier.weight(0.2f))

            OutlinedTextField(value = viewModel.fatPercentage,
                modifier = Modifier.weight(1f),
                onValueChange = {
                    viewModel.fatPercentage = it
                },
                keyboardOptions = keyboardOptions,
                colors = colors,
                placeholder = { Text("Yağ oranı") })
        }
        Row(
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp)
        ) {
            OutlinedTextField(value = viewModel.musclePercentage,
                modifier = Modifier.weight(1f),
                onValueChange = {
                    viewModel.musclePercentage = it
                },
                keyboardOptions = keyboardOptions,
                colors = colors,
                placeholder = { Text("Kas oranı") })

            Spacer(Modifier.weight(0.2f))

            OutlinedTextField(value = viewModel.waterPercentage,
                modifier = Modifier.weight(1f),
                onValueChange = {
                    viewModel.waterPercentage = it
                },
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Done, keyboardType = KeyboardType.Number
                ),
                keyboardActions = KeyboardActions(onDone = {
                    viewModel.insertWeight()
                }),
                colors = colors,
                placeholder = { Text("Su oranı") })
        }

        AnimatedVisibility(visible = viewModel.fatPercentage != "") {
            viewModel.calculateFat()
            Text(text = "Yağ : ${viewModel.fatWeight} Kg")
        }

        AnimatedVisibility(visible = viewModel.musclePercentage != "") {
            viewModel.calculateMuscle()
            Text(text = "Kas : ${viewModel.muscleWeight} Kg")
        }
        AnimatedVisibility(visible = viewModel.waterPercentage != "") {
            viewModel.calculateWater()
            Text(text = "Su : ${viewModel.waterWeight} Kg")
        }

        viewModel.fetchWeights()
        AnimatedVisibility(visible = viewModel.weights.isEmpty().not()) {
            LazyColumn(
                contentPadding = PaddingValues(vertical = 16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(items = viewModel.weights, itemContent = {
                    WeightItem(weight = it)
                })
            }
        }
    }
}

@Composable
fun WeightItem(weight: Weight) {
    Column {
        Row {
            Text(text = "${weight.date} - ${weight.weight} Kg")
        }
        Row {
            Text(text = "Yağ: ${weight.fat} kg, Su: ${weight.water} kg, Kas: ${weight.muscle} kg")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    SimpleCalculatorTheme {
        //SimpleCalculatorComponents()
    }
}