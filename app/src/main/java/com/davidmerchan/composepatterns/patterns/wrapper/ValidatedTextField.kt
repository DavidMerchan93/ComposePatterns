package com.davidmerchan.composepatterns.patterns.wrapper

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp


@Composable
fun ValidatorFormScreen(modifier: Modifier = Modifier) {

    var text by remember { mutableStateOf("") }

    Scaffold(
        modifier = modifier.fillMaxSize()
    ) { padding ->
        Column(Modifier.padding(padding)) {
            ValidatedTextField(
                value = text,
                onValueChange = { text = it },
                label = "Write something",
                isValid = { it.length > 8 },
                errorMessage = "The size should be upper 8"
            )
        }
    }
}

/**
* Con el patron wrapper podemos agrupar varios elementos en uno solo
* */
@Composable
fun ValidatedTextField(
    modifier: Modifier = Modifier,
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    isValid: (String) -> Boolean,
    errorMessage: String? = null
) {
    var showError by remember { mutableStateOf(false) }

    Column(modifier = modifier) {
        TextField(
            value = value,
            onValueChange = {
                onValueChange(it)
                showError = !isValid(it)
            },
            label = {
                Text(text = label)
            },
            isError = showError,
            modifier = Modifier.fillMaxWidth()
        )
        if (showError) {
            Text(
                text = errorMessage ?: "",
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            )
        }
    }
}