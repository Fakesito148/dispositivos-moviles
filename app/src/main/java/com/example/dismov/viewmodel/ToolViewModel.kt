// ToolViewModel.kt
package com.example.dismov.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dismov.models.Tool
import com.example.dismov.network.RetrofitClient
import com.example.dismov.utils.TokenManager
import kotlinx.coroutines.launch
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.State

class ToolViewModel : ViewModel() {

    private val _tools = mutableStateOf<List<Tool>>(emptyList())
    val tools: State<List<Tool>> = _tools

    private val toolService = RetrofitClient.toolService

    fun loadTools() {
        viewModelScope.launch {
            try {
                val token = TokenManager.getToken()
                if (token.isNullOrBlank()) {
                    println("No hay token disponible.")
                    return@launch
                }

                val result = toolService.getAvailableTools("Bearer $token")
                _tools.value = result.filter { it.available }
            } catch (e: Exception) {
                println("Error al obtener herramientas: ${e.localizedMessage}")
                e.printStackTrace()
            }
        }
    }
}
