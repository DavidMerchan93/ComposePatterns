package com.davidmerchan.composepatterns

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.davidmerchan.composepatterns.patterns.builder_pattern.DynamicFormScreen
import com.davidmerchan.composepatterns.patterns.compound_component.UserProfileScreen
import com.davidmerchan.composepatterns.patterns.slot_pattern.ProfileScreen
import com.davidmerchan.composepatterns.patterns.state_hoisting.CounterScreen
import com.davidmerchan.composepatterns.patterns.wrapper.ValidatorFormScreen
import com.davidmerchan.composepatterns.ui.theme.ComposePatternsTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ComposePatternsTheme {
                CounterScreen()
            }
        }
    }
}
