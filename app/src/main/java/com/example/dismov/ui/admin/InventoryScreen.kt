package com.example.dismov.ui.admin

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.dismov.viewmodel.ToolViewModel
import java.io.File

@Composable
fun InventoryScreen(viewModel: ToolViewModel, token: String) {
    val tools = viewModel.tools
    val errorMessage = viewModel.errorMessage

    var showForm by remember { mutableStateOf(false) }
    var name by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var quantity by remember { mutableStateOf("") }

    LaunchedEffect(Unit) {
        viewModel.fetchTools(token)
    }

    Column(modifier = Modifier
        .fillMaxSize()
        .padding(16.dp)) {

        Text("Inventario", style = MaterialTheme.typography.headlineMedium)

        if (errorMessage != null) {
            Text(text = errorMessage, color = Color.Red)
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = { showForm = !showForm }) {
            Text(if (showForm) "Cancelar" else "Añadir herramienta")
        }

        if (showForm) {
            Spacer(modifier = Modifier.height(16.dp))
            OutlinedTextField(
                value = name,
                onValueChange = { name = it },
                label = { Text("Nombre") },
                modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                value = description,
                onValueChange = { description = it },
                label = { Text("Descripción") },
                modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                value = quantity,
                onValueChange = { quantity = it },
                label = { Text("Cantidad disponible") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(8.dp))
            Button(onClick = {
                val q = quantity.toIntOrNull() ?: 0
                viewModel.addTool(name, description, q, null, token)
                name = ""
                description = ""
                quantity = ""
                showForm = false
            }) {
                Text("Guardar herramienta")
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        LazyColumn {
            items(tools) { tool ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    elevation = CardDefaults.cardElevation()
                ) {
                    Column(Modifier.padding(16.dp)) {
                        Text("Nombre: ${tool.name}", style = MaterialTheme.typography.titleMedium)
                        Text("Descripción: ${tool.description}")
                        Text("Stock: ${tool.availableQuantity}")
                        tool.imageUrl?.let {
                            AsyncImage(
                                model = it,
                                contentDescription = null,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(150.dp),
                                contentScale = ContentScale.Crop
                            )
                        }

                        Spacer(modifier = Modifier.height(8.dp))

                        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                            Button(
                                onClick = {
                                    // Aquí deberías abrir un diálogo o navegar a otra pantalla para editar
                                    // Por ejemplo (ejemplo básico sin imagen):
                                    viewModel.updateTool(
                                        toolId = tool._id,
                                        name = tool.name,
                                        description = tool.description,
                                        quantity = tool.availableQuantity,
                                        imageFile = null,
                                        token = token
                                    )
                                },
                                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
                            ) {
                                Text("Editar")
                            }

                            Button(
                                onClick = {
                                    viewModel.deleteTool(tool._id, token)
                                },
                                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error)
                            ) {
                                Text("Eliminar")
                            }
                        }
                    }
                }
            }
        }
    }
}
