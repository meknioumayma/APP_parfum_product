package com.example.myapplication.database

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import com.example.myapplication.data.Product

class ProductDatabase(context: Context) {

    private val dbHelper: DBHelper = DBHelper(context)

    // Ajouter un produit
    fun addProduct(product: Product) {
        val db = dbHelper.writableDatabase
        val values = ContentValues().apply {
            put("name", product.name)
            put("price", product.price)
            put("description", product.description)
            put("imageUrl", product.imageUrl)
        }
        db.insert("products", null, values)
        db.close()
    }

    // Récupérer tous les produits
    fun getAllProducts(): List<Product> {
        val db = dbHelper.readableDatabase
        val cursor: Cursor = db.query(
            "products",
            arrayOf("id", "name", "price", "description", "imageUrl"),
            null, null, null, null, null
        )

        val products = mutableListOf<Product>()
        with(cursor) {
            while (moveToNext()) {
                val id = getLong(getColumnIndexOrThrow("id"))
                val name = getString(getColumnIndexOrThrow("name"))
                val price = getDouble(getColumnIndexOrThrow("price"))
                val description = getString(getColumnIndexOrThrow("description"))
                val imageUrl = getInt(getColumnIndexOrThrow("imageUrl"))

            }
        }
        cursor.close()
        db.close()
        return products
    }

    // Supprimer un produit par ID
    fun deleteProduct(productId: Long) {
        val db = dbHelper.writableDatabase
        db.delete("products", "id = ?", arrayOf(productId.toString()))
        db.close()
    }

    // Mettre à jour un produit
    fun updateProduct(productId: Long, name: String, price: String, description: String) {
        val db = dbHelper.writableDatabase
        val values = ContentValues().apply {
            put("name", name)
            put("price", price.toDouble()) // Convertir le prix de String à Double
            put("description", description)
        }
        db.update("products", values, "id = ?", arrayOf(productId.toString())) // Convertir en String
        db.close()
    }

    // Classe interne pour gérer la base de données
    private class DBHelper(context: Context) : android.database.sqlite.SQLiteOpenHelper(context, DB_NAME, null, DB_VERSION) {

        override fun onCreate(db: SQLiteDatabase) {
            val createTableQuery = """
                CREATE TABLE products (
                    id INTEGER PRIMARY KEY AUTOINCREMENT,
                    name TEXT NOT NULL,
                    price REAL NOT NULL,
                    description TEXT,
                    imageUrl INTEGER NOT NULL
                )
            """
            db.execSQL(createTableQuery)
        }

        override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
            db.execSQL("DROP TABLE IF EXISTS products")
            onCreate(db)
        }
    }

    companion object {
        private const val DB_NAME = "product_database"
        private const val DB_VERSION = 1
    }
}
