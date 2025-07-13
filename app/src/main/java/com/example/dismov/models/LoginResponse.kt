package com.example.dismov.models

data class LoginResponse(
    val status: String,
    val token: String,
    val data: LoginData
)

data class LoginData(
    val user: User
)
