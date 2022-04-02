package com.simplecalculator

import android.os.Bundle
import android.widget.EditText
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.SemanticsProperties.EditableText
import androidx.compose.ui.text.InternalTextApi
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
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
                    SimpleTextInputComponent()
                }
            }
        }
    }
}

@Composable
fun SimpleTextInputComponent() {
    Surface(color = Color.LightGray, modifier = Modifier.padding(16.dp)) {
        var textValue by remember { mutableStateOf(TextFieldValue("Kilo")) }
        OutlinedTextField(onValueChange = )

        BasicTextField(
            value = textValue,

            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            // Update value of textValue with the latest value of the text field
            onValueChange = {
                textValue = it
            }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    SimpleCalculatorTheme {
        SimpleTextInputComponent()
    }
}