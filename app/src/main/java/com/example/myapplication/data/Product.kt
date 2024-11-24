package com.example.myapplication.data

data class Product(
    val id: Long = 0,  // ID should be a Long or Int
    val name: String,
    val price: Double,
    val description: String,
    val imageUrl: Int  // Resource ID for the image
)