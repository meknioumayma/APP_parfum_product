package com.example.myapplication.database

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.myapplication.data.Product

class DBHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "products.db"
        private const val DATABASE_VERSION = 1
        private const val TABLE_NAME = "product"
        private const val COLUMN_ID = "id"
        private const val COLUMN_NAME = "name"
        private const val COLUMN_PRICE = "price"
        private const val COLUMN_DESCRIPTION = "description"
        private const val COLUMN_IMAGE_URL = "image_url"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val createTableQuery = """
            CREATE TABLE $TABLE_NAME (
                $COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT,
                $COLUMN_NAME TEXT,
                $COLUMN_PRICE REAL,
                $COLUMN_DESCRIPTION TEXT,
                $COLUMN_IMAGE_URL INTEGER
            )
        """
        db?.execSQL(createTableQuery)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        if (oldVersion < newVersion) {
            db?.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
            onCreate(db)
        }
    }

    // Add a new product
    fun addProduct(product: Product) {
        val db = writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_NAME, product.name)
            put(COLUMN_PRICE, product.price)
            put(COLUMN_DESCRIPTION, product.description)
            put(COLUMN_IMAGE_URL, product.imageUrl)
        }
        db.insert(TABLE_NAME, null, values)
        db.close()
    }

    // Update an existing product
    fun updateProduct(product: Product): Int {
        val db = writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_NAME, product.name)
            put(COLUMN_PRICE, product.price)
            put(COLUMN_DESCRIPTION, product.description)
            put(COLUMN_IMAGE_URL, product.imageUrl)
        }
        val whereClause = "$COLUMN_ID = ?"
        val whereArgs = arrayOf(product.id.toString())
        return db.update(TABLE_NAME, values, whereClause, whereArgs)
    }

    // Delete a product
    fun deleteProduct(product: Product): Int {
        val db = writableDatabase
        val whereClause = "$COLUMN_ID = ?"
        val whereArgs = arrayOf(product.id.toString())
        return db.delete(TABLE_NAME, whereClause, whereArgs)
    }

    fun getAllProducts(): List<Product> {
        val db = readableDatabase
        val cursor: Cursor = db.query(TABLE_NAME, null, null, null, null, null, null)
        val products = mutableListOf<Product>()

        if (cursor.moveToFirst()) {
            do {
                // Retrieve fields from the cursor
                val id = cursor.getLong(cursor.getColumnIndexOrThrow(COLUMN_ID)) // Fix: Fetch ID
                val name = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NAME))
                val price = cursor.getDouble(cursor.getColumnIndexOrThrow(COLUMN_PRICE))
                val description = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DESCRIPTION))
                val imageUrl = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_IMAGE_URL))

                // Create and add the Product object
                products.add(Product(id, name, price, description, imageUrl))
            } while (cursor.moveToNext())
        }
        cursor.close()
        db.close()
        return products
    }}
