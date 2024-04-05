package com.example.bondoman_pdd.data.model

data class ScannerResponse(
    val items: Items
)

data class Items(
    val items: List<ItemsInfo>
)

data class ItemsInfo(
    val name: String,
    val qty: Int,
    val price: Float
)