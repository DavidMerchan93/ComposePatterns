package com.davidmerchan.composepatterns.patterns.compound_component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp


@Composable
fun UserProfileScreen(modifier: Modifier = Modifier) {
    val user by remember { mutableStateOf(User("David", 31)) }

    Scaffold(
        modifier = modifier.fillMaxSize()
    ) { innerPadding ->

        UserProfileCard(
            modifier = Modifier.padding(innerPadding),
            user = user,
            content = {
                UserCard()
                CustomForm { name ->
                    user.name = name
                    updateNameUser(name)
                }
            }
        )
        // No puedo llamar aca custom form porque no hace parte del scope
        //CustomForm()
    }
}

@Composable
fun UserProfileCard(
    modifier: Modifier = Modifier,
    user: User,
    content: @Composable UserDataScope.() -> Unit // Literal function with receptor
) {
    val scope = remember(user) { DefaultUserDataScope(user) }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text("User Profile", modifier = Modifier.padding(10.dp))
        Spacer(Modifier.height(20.dp))
        HorizontalDivider()
        scope.content()
    }
}

@Composable
fun UserDataScope.UserCard(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Name: ${user.name}")
        Spacer(Modifier.height(20.dp))
        Text("Age: ${user.age}")
        HorizontalDivider()
        Spacer(Modifier.height(20.dp))
    }
}

@Composable
fun UserDataScope.CustomForm(
    modifier: Modifier = Modifier,
    updateName: (String) -> Unit
) {
    var name by remember { mutableStateOf("") }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        OutlinedTextField(
            value = name,
            onValueChange = {
                name = it
            }
        )
        Button(
            onClick = {
                updateName(name)
            }
        ) {
            Text("Update name")
        }
    }
}
