package com.example.dismov.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dismov.models.Loan
import com.example.dismov.network.ApiClient
import kotlinx.coroutines.launch
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue

class LoanViewModel : ViewModel() {

    private val loanService = ApiClient.loanService

    var loans by mutableStateOf<List<Loan>>(emptyList())
        private set

    var errorMessage by mutableStateOf<String?>(null)

    fun fetchAllLoans(token: String) {
        viewModelScope.launch {
            try {
                val response = loanService.getAllLoans("Bearer $token")
                loans = response.data.loans
                if (response.status == "success") {
                    loans = response.data.loans
                } else {
                    errorMessage = "Error al obtener préstamos"
                }
            } catch (e: Exception) {
                errorMessage = "Error al obtener préstamos: ${e.message}"
            }
        }
    }

    fun returnLoan(token: String, loanId: String) {
        viewModelScope.launch {
            try {
                loanService.returnLoan("Bearer $token", loanId)
            } catch (e: Exception) {
                errorMessage = "Error al devolver herramienta: ${e.message}"
            }
        }
    }
}
