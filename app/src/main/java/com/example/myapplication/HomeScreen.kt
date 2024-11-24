package com.example.myapplication

import android.os.Bundle
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.myapplication.database.DBHelper
import com.example.myapplication.data.Product

@Composable
fun HomeScreen(navController: NavHostController, context: android.content.Context) {
    val dbHelper = DBHelper(context)
    var products by remember { mutableStateOf(dbHelper.getAllProducts()) }

    // Variables pour gérer l'état de la boîte de dialogue d'édition
    var showEditDialog by remember { mutableStateOf(false) }
    var selectedProduct by remember { mutableStateOf<Product?>(null) }

    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp)
    ) {
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.weight(1f)
        ) {
            items(products) { product ->
                ProductItem(
                    product = product,
                    onClick = {
                        navController.navigate(
                            "product_detail/${product.name}/${product.price}/${product.description}/${product.imageUrl}"
                        )
                    },
                    onEdit = {
                        // Ouvrir la boîte de dialogue avec le produit sélectionné
                        selectedProduct = product
                        showEditDialog = true
                    },
                    onDelete = {
                        dbHelper.deleteProduct(product)
                        products = dbHelper.getAllProducts() // Mettre à jour la liste
                    }
                )
            }
        }

        // Bouton pour accéder à AddProductScreen
        Button(
            onClick = { navController.navigate("add_product") },
            modifier = Modifier.fillMaxWidth().padding(top = 16.dp)
        ) {
            Text(text = "Ajouter un produit")
        }
    }

    // Affichage de la boîte de dialogue pour l'édition
    if (showEditDialog && selectedProduct != null) {
        EditProductDialog(
            product = selectedProduct!!,
            onDismiss = { showEditDialog = false },
            onSave = { updatedProduct ->
                dbHelper.updateProduct(updatedProduct)
                products = dbHelper.getAllProducts() // Mettre à jour la liste
                showEditDialog = false
            }
        )
    }
}
