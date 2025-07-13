package com.example.dismov.network

import com.example.dismov.models.LoanListResponse
import com.example.dismov.models.LoanResponseWrapper
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.PATCH
import retrofit2.http.Path

interface LoanService {

    @GET("api/v1/loans")
    suspend fun getAllLoans(
        @Header("Authorization") token: String
    ): LoanListResponse // ← CAMBIAR AQUÍ TAMBIÉN


    @PATCH("/api/v1/loans/{id}/return")
    suspend fun returnLoan(
        @Header("Authorization") token: String,
        @Path("id") loanId: String
    )
}
