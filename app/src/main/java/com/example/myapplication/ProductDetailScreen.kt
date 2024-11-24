package com.example.myapplication

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.myapplication.data.Product
import com.example.myapplication.database.ProductDatabase

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductDetailScreen(navController: NavController, product: Product) {
    // Obtenez le contexte local
    val context = LocalContext.current
    val productDatabase = ProductDatabase(context) // Utilisez le contexte local ici

    Column {
        TopAppBar(
            title = { Text(text = "Détails du produit") },
            navigationIcon = {
                IconButton(onClick = { navController.popBackStack() }) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Retour"
                    )
                }
            },
        )

        Column(modifier = Modifier.padding(16.dp)) {
            Image(
                painter = painterResource(id = product.imageUrl),
                contentDescription = product.name,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(text = "Nom: ${product.name}", modifier = Modifier.padding(vertical = 4.dp))
            Text(text = "Prix: ${product.price} €", modifier = Modifier.padding(vertical = 4.dp))
            Text(text = "Description: ${product.description}", modifier = Modifier.padding(vertical = 4.dp))
        }
    }
}
