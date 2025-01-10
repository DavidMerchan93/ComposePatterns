package com.davidmerchan.composepatterns.patterns.slot_pattern

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun ProfileScreen(modifier: Modifier = Modifier) {
    ProfileCard(
        modifier = modifier.fillMaxSize(),
        header = { SimpleHeader() },
        body = { CustomBody() },
        footer = { SimpleFooter() }
    )
}

@Composable
fun ProfileCard(
    modifier: Modifier = Modifier,
    header: @Composable (modifier: Modifier) -> Unit,
    body: @Composable (modifier: Modifier) -> Unit,
    footer: @Composable (modifier: Modifier) -> Unit,
) {
    Scaffold(
        modifier = modifier
    ) { innerPadding ->
        Column(
            modifier = Modifier.padding(innerPadding)
        ) {
            Text("User Profile")
            HorizontalDivider()
            header(Modifier)
            body(Modifier.weight(1f))
            HorizontalDivider()
            footer(Modifier)
        }
    }
}

@Composable
fun SimpleHeader(modifier: Modifier = Modifier) {
    Row(modifier = modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
        Icon(
            imageVector = Icons.Default.Person,
            contentDescription = "User Icon"
        )
        Text(
            text = "John Doe",
            style = MaterialTheme.typography.titleMedium
        )
    }
}

@Composable
fun CustomBody(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier.padding(20.dp)
    ) {
        Text("Simple Body")
        HorizontalDivider(Modifier.padding(16.dp))
        Row(modifier = Modifier.fillMaxWidth()) {
            Box(modifier = Modifier
                .size(30.dp)
                .background(Color.Red))
            Box(modifier = Modifier
                .size(50.dp)
                .background(Color.Blue))
        }
    }
}

@Composable
fun SimpleFooter(modifier: Modifier = Modifier) {
    Row(modifier = modifier.fillMaxWidth().padding(20.dp), verticalAlignment = Alignment.CenterVertically) {
        Text(
            text = "Contact Information",
            style = MaterialTheme.typography.labelSmall,
            modifier = Modifier.weight(1f)
        )
        Text(
            text = "+57 3023224566",
            style = MaterialTheme.typography.labelSmall
        )
    }

}