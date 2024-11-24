package com.example.myapplication



import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.myapplication.data.Product

@Composable
fun ProductItem(
    product: Product,
    onClick: () -> Unit,
    onEdit: () -> Unit,
    onDelete: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable { onClick() }
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            // Ligne contenant l'image et les boutons d'action (modifier, supprimer)
            Row(
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                // Image du produit
                Image(
                    painter = painterResource(id = product.imageUrl),
                    contentDescription = product.name,
                    modifier = Modifier.size(80.dp)
                )

                // Boutons d'action (modifier et supprimer)
                Column(
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    // Bouton pour modifier
                    Button(onClick = onEdit) {
                        Text(text = "Modifier")
                    }

                    // Bouton pour supprimer
                    Button(onClick = onDelete) {
                        Text(text = "Supprimer")
                    }
                }
            }

            Spacer(modifier = Modifier.height(8.dp))
            Text(text = product.name) // Nom du produit
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = "Prix : ${product.price} €") // Prix du produit
        }
    }
}
