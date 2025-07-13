package com.example.dismov.ui.user

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.graphics.Color
import com.example.dismov.viewmodel.ToolViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AvailableToolsScreen(viewModel: ToolViewModel, token: String) {
    val tools by remember { derivedStateOf { viewModel.tools } }
    val errorMessage by remember { derivedStateOf { viewModel.errorMessage } }

    val coroutineScope = rememberCoroutineScope()
    var loadingToolId by remember { mutableStateOf<String?>(null) }
    var successMessage by remember { mutableStateOf<String?>(null) }

    LaunchedEffect(Unit) {
        viewModel.fetchTools(token)
    }

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Herramientas Disponibles") })
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
        ) {
            if (errorMessage != null) {
                Text(text = errorMessage ?: "Error desconocido", color = Color.Red)
            }
            if (successMessage != null) {
                Text(text = successMessage ?: "", color = Color.Green)
            }

            LazyColumn {
                items(tools) { tool ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp),
                        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            tool.imageUrl?.let {
                                AsyncImage(
                                    model = it,
                                    contentDescription = null,
                                    modifier = Modifier
                                        .size(100.dp)
                                        .padding(end = 16.dp),
                                    contentScale = ContentScale.Crop
                                )
                            }

                            Column(
                                modifier = Modifier.weight(1f)
                            ) {
                                Text(tool.name, style = MaterialTheme.typography.titleMedium)
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(tool.description, style = MaterialTheme.typography.bodyMedium)
                                Spacer(modifier = Modifier.height(4.dp))
                                Text("Stock: ${tool.availableQuantity}", style = MaterialTheme.typography.bodySmall)
                            }

                            Spacer(modifier = Modifier.width(8.dp))

                            Button(
                                onClick = {
                                    coroutineScope.launch {
                                        loadingToolId = tool._id
                                        successMessage = null
                                        try {
                                            viewModel.requestLoan(
                                                tool._id,
                                                token,
                                                onSuccess = {
                                                    successMessage = "Préstamo solicitado para ${tool.name}"
                                                    viewModel.fetchTools(token)
                                                },
                                                onError = {
                                                    successMessage = "Error al pedir prestada la herramienta: $it"
                                                }
                                            )

                                        } finally {
                                            loadingToolId = null
                                        }
                                    }
                                },
                                enabled = tool.availableQuantity > 0 && loadingToolId != tool._id
                            ) {
                                if (loadingToolId == tool._id) {
                                    CircularProgressIndicator(
                                        color = MaterialTheme.colorScheme.onPrimary,
                                        modifier = Modifier.size(16.dp),
                                        strokeWidth = 2.dp
                                    )
                                } else {
                                    Text("Pedir")
                                }
                            }

                        }
                    }
                }
            }

        }
    }
}
