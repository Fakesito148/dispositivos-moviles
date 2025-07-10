package com.example.dismov.network

import com.example.dismov.models.Tool
import retrofit2.Call
import retrofit2.http.GET

interface ToolService {
    @GET("tools")
    fun getAllTools(): Call<List<Tool>>
}
