package com.example.bondoman_pdd.data.model

import java.io.File
import com.google.gson.annotations.SerializedName

data class ScannerRequest(
    val file: File
)

data class ScannerResponse(
    val items: Items
)

data class Items(
    val items: List<ItemsInfo>
)

data class ItemsInfo(
    val name: String,
    val qty: Int,
    val price: Double
)