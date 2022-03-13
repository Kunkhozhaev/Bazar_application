package com.example.bazar.data.model

data class Product(
    val id: String = "",
    var productName: String = "",
    val time: Long = 0L,
    var checked: Boolean = false

)