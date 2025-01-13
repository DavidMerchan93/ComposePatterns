package com.davidmerchan.composepatterns.patterns.builder_pattern

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.davidmerchan.composepatterns.patterns.wrapper.ValidatedTextField

/**
Por ejemplo, si queremos crear un componente que admita diferentes tipos de campos,
como email, number o text, para agregar los que necesite podemos crear un FormBuilder
que se encargaría de definir los tipos de campo que necesitamos.
 */
sealed interface FormField {
    data class Text(
        val label: String,
        val initialValue: String,
        val isValid: (String) -> Boolean,
        val errorMessage: String? = null
    ) : FormField

    data class Button(
        val title: String,
        val background: Color,
        val disableBackground: Color = Color.Gray,
        val textColor: Color,
        val onClick: () -> Unit,
        val isEnabled: () -> Boolean = { true }
    ) : FormField
}

class FormBuilder {

    private val fields = mutableListOf<FormField>()

    // Defino un textfield simple
    fun simpleTextField(
        label: String,
        initialValue: String = "",
        isValid: (String) -> Boolean,
        errorMessage: String? = null
    ) {
        fields.add(
            FormField.Text(
                label = label,
                initialValue = initialValue,
                isValid = isValid,
                errorMessage = errorMessage
            )
        )
    }

    // Defino un textfield de tipo email
    fun emailTextField(
        label: String,
        initialValue: String = "",
        errorMessage: String? = "Invalid email format"
    ) {
        fields.add(
            FormField.Text(
                label = label,
                initialValue = initialValue,
                isValid = { text ->
                    android.util.Patterns.EMAIL_ADDRESS.matcher(text).matches()
                },
                errorMessage = errorMessage
            )
        )
    }

    // Defino un textfield de tipo Numerico
    fun numericTextField(
        label: String,
        initialValue: String = "0",
        isValid: (String) -> Boolean = { it.toIntOrNull() != null },
        errorMessage: String? = "Invalid numeric format"
    ) {
        fields.add(
            FormField.Text(
                label = label,
                initialValue = initialValue,
                isValid = isValid,
                errorMessage = errorMessage
            )
        )
    }

    fun simpleButton(
        title: String,
        background: Color = Color.Blue,
        textColor: Color = Color.White,
        onClick: () -> Unit
    ) {
        fields.add(
            FormField.Button(
                title = title,
                background = background,
                textColor = textColor,
                onClick = onClick,
            )
        )
    }

    fun enableButton(
        title: String,
        background: Color = Color.Red,
        textColor: Color = Color.White,
        onClick: () -> Unit,
        isEnabled: () -> Boolean
    ) {
        fields.add(
            FormField.Button(
                title = title,
                background = background,
                textColor = textColor,
                onClick = onClick,
                isEnabled = isEnabled
            )
        )
    }

    internal fun build(): List<FormField> = fields
}

/**
 * Ahora creamos nuestro componente en compose
 */
@Composable
fun DynamicForm(
    modifier: Modifier = Modifier,
    formBuilder: FormBuilder.() -> Unit,
    onSubmit: () -> Unit
) {
    val fields = FormBuilder().apply(formBuilder).build()
    val values = remember { mutableStateMapOf<String, String>() }
    val errors = remember { mutableStateMapOf<String, Boolean>() }

    // Renderizamos los campos
    Column(modifier = modifier) {
        fields.forEach { field ->
            when (field) {
                is FormField.Text -> {
                    var value by remember { mutableStateOf(field.initialValue) }

                    ValidatedTextField(
                        value = value,
                        onValueChange = { text ->
                            value = text
                            values[field.label] = text
                            errors[field.label] = !field.isValid(text)
                        },
                        label = field.label,
                        isValid = field.isValid,
                        errorMessage = field.errorMessage
                    )
                }

                is FormField.Button -> {
                    Button(
                        onClick = {
                            println("Button clicked")
                        },
                        enabled = field.isEnabled() && errors.filter { it.value }.isEmpty(),
                        colors = ButtonDefaults.buttonColors(
                            contentColor = field.background,
                            disabledContentColor = field.disableBackground
                        ),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = field.title,
                            color = field.textColor
                        )
                    }
                }
            }
        }
    }
}

/**
 * Ahora podemos crear nuestro formulario dinamico
 */
@Composable
fun DynamicFormScreen() {
    Scaffold(modifier = Modifier.fillMaxSize()) { padding ->
        DynamicForm(
            modifier = Modifier.padding(padding),
            formBuilder = {
                simpleTextField(
                    label = "Nombre",
                    initialValue = "",
                    isValid = { it.isNotBlank() },
                    errorMessage = "Debe ingresar un nombre"
                )
                simpleTextField(
                    label = "Apellido",
                    initialValue = "",
                    isValid = { it.isNotBlank() },
                    errorMessage = "Debe ingresar un apellido"
                )
                emailTextField(
                    label = "Email",
                    initialValue = "",
                    errorMessage = "Debe ingresar un email válido"
                )
                numericTextField(
                    label = "Edad",
                    initialValue = "0",
                    isValid = { it.toIntOrNull() in 1..90 },
                    errorMessage = "Debe ingresar una edad válida"
                )
                simpleButton(
                    title = "Enviar",
                    background = Color.Green,
                    textColor = Color.White,
                    onClick = {
                        println("Button clicked")
                    },
                )
                enableButton(
                    title = "Deshabilitar",
                    background = Color.Red,
                    textColor = Color.White,
                    onClick = {
                        println("Button clicked")
                    },
                    isEnabled = { true }
                )
            },
            onSubmit = {
                println("Form submitted")
            }
        )
    }
}
