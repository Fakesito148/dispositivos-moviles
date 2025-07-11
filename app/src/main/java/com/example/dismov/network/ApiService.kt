package com.example.dismov.network

import com.example.dismov.models.LoginRequest
import com.example.dismov.models.LoginResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {
    @POST("/login")
    suspend fun login(@Body request: LoginRequest): LoginResponse

}

