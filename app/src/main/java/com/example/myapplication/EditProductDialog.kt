package com.example.myapplication

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.text.input.KeyboardOptions
import androidx.compose.ui.text.input.KeyboardType
import com.example.myapplication.data.Product
import com.example.myapplication.database.DBHelper

@Composable
fun EditProductDialog(
    product: Product,
    onDismiss: () -> Unit,
    onSave: (Product) -> Unit
) {
    // Variables pour stocker les champs modifiés
    var name by remember { mutableStateOf(product.name) }
    var price by remember { mutableStateOf(product.price.toString()) }
    var description by remember { mutableStateOf(product.description) }
    var imageUrl by remember { mutableStateOf(product.imageUrl.toString()) }

    AlertDialog(
        onDismissRequest = { onDismiss() },
        title = { Text(text = "Modifier le produit") },
        text = {
            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                // Champ pour le nom
                OutlinedTextField(
                    value = name,
                    onValueChange = { name = it },
                    label = { Text("Nom") },
                    modifier = Modifier.fillMaxWidth()
                )

                // Champ pour le prix
                OutlinedTextField(
                    value = price,
                    onValueChange = { price = it },
                    label = { Text("Prix (€)") },
                    modifier = Modifier.fillMaxWidth(),
                    keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number)
                )

                // Champ pour la description
                OutlinedTextField(
                    value = description,
                    onValueChange = { description = it },
                    label = { Text("Description") },
                    modifier = Modifier.fillMaxWidth()
                )

                // Champ pour l'ID de l'image
                OutlinedTextField(
                    value = imageUrl,
                    onValueChange = { imageUrl = it },
                    label = { Text("ID de l'image") },
                    modifier = Modifier.fillMaxWidth(),
                    keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number)
                )
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    // Valider et sauvegarder les modifications
                    val updatedProduct = product.copy(
                        name = name,
                        price = price.toDoubleOrNull() ?: 0.0,  // Si prix invalide, on met 0.0
                        description = description,
                        imageUrl = imageUrl.toIntOrNull() ?: product.imageUrl // Si ID d'image invalide, on garde l'ID actuel
                    )
                    onSave(updatedProduct) // Enregistrer les modifications
                }
            ) {
                Text("Sauvegarder")
            }
        },
        dismissButton = {
            Button(onClick = { onDismiss() }) {
                Text("Annuler")
            }
        }
    )
}
