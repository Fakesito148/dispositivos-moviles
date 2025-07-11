// models/Tool.kt
package com.example.dismov.models

data class Tool(
    val _id: String,
    val name: String,
    val description: String,
    val available: Boolean // ✅ Este campo es necesario
)
