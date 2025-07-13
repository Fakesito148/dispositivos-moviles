package com.example.dismov.ui.admin

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.graphics.Color
import com.example.dismov.viewmodel.LoanViewModel
import com.example.dismov.viewmodel.ToolViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.ui.Alignment
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.dismov.models.LoanUser

@Composable
fun HistoryScreen(
    viewModel: LoanViewModel,
    token: String,
    toolViewModel: ToolViewModel = viewModel()
) {
    val loans = viewModel.loans
    val tools = toolViewModel.tools
    val errorMessage = viewModel.errorMessage

    LaunchedEffect(Unit) {
        viewModel.fetchAllLoans(token)
        toolViewModel.fetchTools(token)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "Historial de préstamos",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        if (errorMessage != null) {
            Text(
                text = errorMessage,
                color = Color.Red,
                modifier = Modifier.padding(bottom = 16.dp)
            )
        }

        LazyColumn(verticalArrangement = Arrangement.spacedBy(12.dp)) {
            items(loans) { loan ->
                val matchedTool = tools.find { it._id == loan.tool?._id }

                Card(
                    modifier = Modifier.fillMaxWidth(),
                    elevation = CardDefaults.cardElevation()
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text("Herramienta: ${matchedTool?.name ?: "Desconocida"}", fontWeight = FontWeight.Bold, fontSize = 18.sp)
                        Text("Usuario: ${loan.user ?: "Desconocido"}")
                        //Text("Email: ${loan.userEmail ?: "Desconocido"}")
                        Text("Fecha de préstamo: ${loan.startDate}")
                        Text("Fecha de devolución: ${loan.endDate ?: "No devuelto"}")
                        Text("Notas: ${loan.notes ?: "Sin notas"}")
                    }
                }
            }
        }
    }
}
