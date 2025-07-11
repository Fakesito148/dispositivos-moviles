// ApiClient.kt
package com.example.dismov.network

import com.example.dismov.utils.TokenManager
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiClient {
    private const val BASE_URL = "http://10.0.2.2:3000"

    private val authInterceptor = object : Interceptor {
        override fun intercept(chain: Interceptor.Chain): Response {
            val requestBuilder = chain.request().newBuilder()

            // Obtener token guardado
            val token = TokenManager.getToken()
            if (!token.isNullOrEmpty()) {
                requestBuilder.addHeader("Authorization", "Bearer $token")
            }

            return chain.proceed(requestBuilder.build())
        }
    }

    private val okHttpClient = OkHttpClient.Builder()
        .addInterceptor(authInterceptor)
        .build()

    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(okHttpClient)  // <---- Importante! Añadimos el cliente con interceptor
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val apiService: AuthService = retrofit.create(AuthService::class.java)
}
