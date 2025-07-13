package com.example.dismov.ui.user

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import androidx.compose.ui.layout.ContentScale
import com.example.dismov.viewmodel.LoanViewModel
import com.example.dismov.viewmodel.ToolViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyToolsScreen(
    token: String,
    userId: String
) {
    val loanViewModel: LoanViewModel = viewModel()
    val toolViewModel: ToolViewModel = viewModel()

    val loans = loanViewModel.loans
    val tools = toolViewModel.tools
    val errorMessage = loanViewModel.errorMessage

    val coroutineScope = rememberCoroutineScope()
    var loadingLoanId by remember { mutableStateOf<String?>(null) }
    var successMessage by remember { mutableStateOf<String?>(null) }

    LaunchedEffect(Unit) {
        loanViewModel.fetchAllLoans(token)
        toolViewModel.fetchTools(token)
    }

    Scaffold(
        topBar = { TopAppBar(title = { Text("Mis Herramientas") }) }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
        ) {
            errorMessage?.let {
                Text(text = it, color = Color.Red)
            }

            successMessage?.let {
                Text(text = it, color = Color.Green)
            }

            val myLoans = loans.filter {
                it.user?._id == userId && it.status == "activo"
            }

            LazyColumn {
                items(myLoans) { loan ->
                    val matchedTool = tools.find { it._id == loan.tool?._id }

                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp),
                        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text(
                                text = matchedTool?.name ?: "Herramienta desconocida",
                                style = MaterialTheme.typography.titleMedium
                            )
                            Text(
                                text = matchedTool?.description ?: "Sin descripción",
                                style = MaterialTheme.typography.bodyMedium
                            )
                            Text(
                                text = "Fecha de entrega: ${loan.endDate?.substring(0, 10) ?: "No definida"}",
                                style = MaterialTheme.typography.bodySmall
                            )

                            matchedTool?.image?.let { imagePath ->
                                AsyncImage(
                                    model = "http://10.0.2.2:3000/$imagePath",
                                    contentDescription = null,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(150.dp)
                                        .padding(top = 8.dp),
                                    contentScale = ContentScale.Crop
                                )
                            }

                            Spacer(modifier = Modifier.height(8.dp))

                            Button(
                                onClick = {
                                    coroutineScope.launch {
                                        loadingLoanId = loan._id
                                        successMessage = null
                                        try {
                                            loanViewModel.returnLoan(token, loan._id)
                                            successMessage = "Herramienta devuelta exitosamente"
                                            loanViewModel.fetchAllLoans(token)
                                            toolViewModel.fetchTools(token)
                                        } catch (e: Exception) {
                                            successMessage = "Error al devolver herramienta"
                                        } finally {
                                            loadingLoanId = null
                                        }
                                    }
                                },
                                enabled = loadingLoanId != loan._id
                            ) {
                                if (loadingLoanId == loan._id) {
                                    CircularProgressIndicator(
                                        color = MaterialTheme.colorScheme.onPrimary,
                                        modifier = Modifier.size(16.dp),
                                        strokeWidth = 2.dp
                                    )
                                } else {
                                    Text("Devolver")
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
