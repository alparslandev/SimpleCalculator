package com.simplecalculator

import android.os.Bundle
import android.view.WindowManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import com.simplecalculator.storage.DefaultLocalStorageImpl
import com.simplecalculator.storage.LocalStorage
import com.simplecalculator.ui.theme.SimpleCalculatorTheme

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
        imeAction = ImeAction.Next,
        keyboardType = KeyboardType.Number
    )
    val colors = TextFieldDefaults.textFieldColors(
        textColor = Color.Black,
        backgroundColor = Color.Transparent
    )
    Column(
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = Modifier.padding(16.dp)
    ) {
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
            ) {
            OutlinedTextField(
                value = viewModel.weight,
                modifier = Modifier.weight(1f),
                onValueChange = {
                    viewModel.weight = it
                },
                keyboardOptions = keyboardOptions,
                colors = colors,
                placeholder = { Text("Kilo") }
            )

            Spacer(Modifier.weight(0.2f))

            OutlinedTextField(
                value = viewModel.fatPercentage,
                modifier = Modifier.weight(1f),
                onValueChange = {
                    viewModel.fatPercentage = it
                },
                keyboardOptions = keyboardOptions,
                colors = colors,
                placeholder = { Text("Yağ oranı") }
            )
        }
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            OutlinedTextField(
                value = viewModel.musclePercentage,
                modifier = Modifier.weight(1f),
                onValueChange = {
                    viewModel.musclePercentage = it
                },
                keyboardOptions = keyboardOptions,
                colors = colors,
                placeholder = { Text("Kas oranı") }
            )

            Spacer(Modifier.weight(0.2f))

            OutlinedTextField(
                value = viewModel.waterPercentage,
                modifier = Modifier.weight(1f),
                onValueChange = {
                    viewModel.waterPercentage = it
                },
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Done,
                    keyboardType = KeyboardType.Number
                ),
                keyboardActions = KeyboardActions(
                    onDone = {
                        viewModel.insertWeight()
                    }
                ),
                colors = colors,
                placeholder = { Text("Su oranı") }
            )
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

        AnimatedVisibility(visible = viewModel.fetchWeights().isNullOrEmpty().not()) {
            Text(text = "var la var oldu la oldu")
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