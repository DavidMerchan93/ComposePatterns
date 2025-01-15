package com.davidmerchan.composepatterns.patterns.decorator_pattern

import android.text.style.ClickableSpan
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

/**
 * Este patron nos ayuda adicionar funcionalidades a nuestros componentes
 * de forma dinamica (decoradores) sin la necesidad de alterar la
 * estructura en si del componente base.
 *
 * En este ejemplo vamos a crear 2 decoradores, un boton con una animacion y otro con un contador
 */
@Composable
fun ClickCounterDecorator(
    modifier: Modifier = Modifier,
    count: Int,
    content: @Composable () -> Unit
) {
    Column(
        modifier = modifier
    ) {
        content()
        HorizontalDivider()
        Text(
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center,
            text = "Click: $count",
            style = MaterialTheme.typography.bodyLarge
        )
    }
}

@Composable
fun PulseEffectDecorator(
    modifier: Modifier = Modifier,
    isPressed: Boolean,
    content: @Composable () -> Unit
) {
    val scale by animateFloatAsState(if (isPressed) 2f else 1f, label = "hello")
    Box(
        modifier = modifier
            .scale(scale)
            .padding(8.dp),
        contentAlignment = Alignment.Center
    ) {
        content()
    }
}

/**
 * Ahora creamos el componente principal que se encargara de envolver ambos decoradores,
 * en caso de agregar mas decoradores podemos ir añadiendolos en este codigo,
 * sin la necesidad de alterar el elemento base
 */
@Composable
fun DecoratedButton(onClick: () -> Unit) {
    var counter by remember { mutableIntStateOf(0) }
    var isPressed by remember { mutableStateOf(false) }

    PulseEffectDecorator(isPressed = isPressed) {
        ClickCounterDecorator(count = counter) {
            Button(
                onClick = {
                    counter++
                    onClick()
                },
                modifier = Modifier.pointerInput(Unit) {
                    detectTapGestures(
                        onPress = {
                            isPressed = true
                            tryAwaitRelease()
                            isPressed = false
                        }
                    )
                }
            ) {
                Text("Tap Here")
            }
        }
    }
}

// Ya por ultimo solo nos queda usar nuestro elemento decorado
@Composable
fun DecoratorCounterScreen(modifier: Modifier = Modifier) {
    Scaffold(modifier = modifier.fillMaxSize()) {
        Column(modifier = Modifier.padding(it)) {
            DecoratedButton {
                println("Click")
            }
            HorizontalDivider()
            DecoratedButton {
                println("Click")
            }
        }
    }
}