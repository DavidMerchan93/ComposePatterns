package com.davidmerchan.composepatterns.patterns.compound_component

import androidx.compose.runtime.Stable

data class User(var name: String, val age: Int)

@Stable
interface UserDataScope {
    val user: User
    fun updateNameUser(name: String)
}

class DefaultUserDataScope(override val user: User) : UserDataScope {
    override fun updateNameUser(name: String) {
        user.name = name
    }
}
