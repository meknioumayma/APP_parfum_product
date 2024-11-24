package com.example.myapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.myapplication.data.Product
import com.example.myapplication.ui.theme.MyApplicationTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MyApplicationTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()
                    NavigationComponent(navController, applicationContext) // Pass context here
                }
            }
        }
    }
}

@Composable
fun NavigationComponent(navController: NavHostController, context: android.content.Context) {
    NavHost(navController, startDestination = "home") {
        // Écran principal
        composable("home") {
            HomeScreen(navController = navController, context = context)
        }

        // Écran pour les détails d'un produit
        composable(
            route = "product_detail/{productName}/{productPrice}/{productDescription}/{productImageUrl}"
        ) { backStackEntry ->
            val product = Product(
                name = backStackEntry.arguments?.getString("productName") ?: "",
                price = backStackEntry.arguments?.getString("productPrice")?.toDouble() ?: 0.0,
                description = backStackEntry.arguments?.getString("productDescription") ?: "",
                imageUrl = backStackEntry.arguments?.getString("productImageUrl")?.toIntOrNull() ?: R.drawable.placeholder
            )

            ProductDetailScreen(navController = navController, product = product)
        }

        // Écran pour ajouter un produit
        composable("add_product") {
            AddProductScreen(navController = navController, context = context)
        }
    }
}
