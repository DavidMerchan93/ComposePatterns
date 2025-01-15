package com.davidmerchan.composepatterns.patterns.state_hoisting

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp


@Composable
fun CounterScreen(modifier: Modifier = Modifier) {
    var count1 by remember { mutableIntStateOf(0) }
    var count2 by remember { mutableIntStateOf(0) }
    var count3 by remember { mutableIntStateOf(0) }

    Scaffold(
        modifier = modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier.padding(it).fillMaxSize(),
            verticalArrangement = Arrangement.Center
        ) {
            Counter(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.Yellow)
                    .padding(12.dp),
                count = count1,
                onDecrement = { count1-- },
                onIncrement = { count1++ },
                onReset = { count1 = 0 }
            )
            Counter(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.Blue)
                    .padding(12.dp),
                count = count2,
                onDecrement = { count2-- },
                onIncrement = { count2++ },
                onReset = { count2 = 0 }
            )
            Counter(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.Red)
                    .padding(12.dp),
                count = count3,
                onDecrement = { count3-- },
                onIncrement = { count3++ },
                onReset = { count3 = 0 }
            )
        }
    }
}

/**
 * En este caso, count es mi estado y este no se maneja de manera interrna si no que se manejara
 * de forma externa, de esta forma Counter puede ser reutilizado varias veces y no estara atado a un solo
 * estado
 */
@Composable
fun Counter(
    modifier: Modifier = Modifier,
    count: Int,
    onIncrement: () -> Unit,
    onDecrement: () -> Unit,
    onReset: () -> Unit
) {
    Column(
        modifier = modifier
    ) {
        Row(
            modifier = Modifier.padding(12.dp)
        ) {
            CounterButton(
                modifier = Modifier.padding(12.dp),
                action = onDecrement,
                title = "-"
            )
            Text(
                modifier = Modifier
                    .padding(12.dp)
                    .weight(1f),
                text = count.toString(),
                style = MaterialTheme.typography.bodyLarge,
                textAlign = TextAlign.Center
            )
            CounterButton(
                modifier = Modifier.padding(12.dp),
                action = onIncrement,
                title = "+"
            )
        }
        CounterButton(
            modifier = Modifier
                .padding(12.dp)
                .fillMaxWidth(),
            action = onReset,
            title = "Reset"
        )
    }
}

@Composable
fun CounterButton(
    modifier: Modifier = Modifier,
    action: () -> Unit,
    title: String
) {
    Button(
        modifier = modifier,
        onClick = {
            action()
        }
    ) {
        Text(
            modifier = Modifier
                .size(60.dp),
            textAlign = TextAlign.Center,
            text = title,
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold
        )
    }
}