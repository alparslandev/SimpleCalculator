package com.simplecalculator

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.simplecalculator.ui.theme.SimpleCalculatorTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val scrollState = rememberScrollState()
            SimpleCalculatorTheme {
                // A surface container using the 'background' color from the theme
                Column(
                    modifier = Modifier.verticalScroll(scrollState)
                ) {
                    SimpleCalculatorComponents()
                }
            }
        }
    }
}

@Composable
fun SimpleCalculatorComponents(viewModel: MainViewModel = viewModel()) {
    val keyboardOptions = KeyboardOptions(
        imeAction = ImeAction.Next,
        keyboardType = KeyboardType.Number
    )
    val colors = TextFieldDefaults.textFieldColors(
        textColor = Color.Black,
        backgroundColor = Color.Transparent
    )
    Column(verticalArrangement = Arrangement.spacedBy(1.dp)) {
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            OutlinedTextField(
                value = viewModel.weight,
                modifier = Modifier
                    .padding(16.dp)
                    .weight(1f),
                onValueChange = {
                    viewModel.weight = it
                },
                keyboardOptions = keyboardOptions,
                colors = colors,
                placeholder = { Text("Kilo") }
            )

            OutlinedTextField(
                value = viewModel.fatPercentage,
                modifier = Modifier
                    .padding(16.dp)
                    .weight(1f),
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
                modifier = Modifier
                    .padding(16.dp)
                    .weight(1f),
                onValueChange = {
                    viewModel.musclePercentage = it
                },
                keyboardOptions = keyboardOptions,
                colors = colors,
                placeholder = { Text("Kas oranı") }
            )

            OutlinedTextField(
                value = viewModel.waterPercentage,
                modifier = Modifier
                    .padding(16.dp)
                    .weight(1f),
                onValueChange = {
                    viewModel.waterPercentage = it
                },
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Done,
                    keyboardType = KeyboardType.Number
                ),
                colors = colors,
                placeholder = { Text("Su oranı") }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    SimpleCalculatorTheme {
        SimpleCalculatorComponents()
    }
}