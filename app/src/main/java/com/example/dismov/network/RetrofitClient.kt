// network/RetrofitClient.kt
package com.example.dismov.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    private const val BASE_URL = "http://10.0.2.2:3000" // o tu IP si estás en dispositivo real

    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    // Servicio de autenticación y usuarios
    val api: ApiService by lazy {
        retrofit.create(ApiService::class.java)
    }

    // Servicio de herramientas (para AvailableToolsScreen)
    val toolService: ToolService by lazy {
        retrofit.create(ToolService::class.java)
    }
}
