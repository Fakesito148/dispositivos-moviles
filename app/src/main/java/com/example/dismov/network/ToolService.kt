// network/ToolService.kt
package com.example.dismov.network

import com.example.dismov.models.Tool
import retrofit2.http.GET
import retrofit2.http.Header

interface ToolService {
    @GET("/tools")
    suspend fun getAvailableTools(
        @Header("Authorization") token: String
    ): List<Tool>

}
