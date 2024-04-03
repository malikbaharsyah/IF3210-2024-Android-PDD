package com.example.bondoman_pdd.data.model

import java.io.File
import com.google.gson.annotations.SerializedName

data class ScannerData(
    @SerializedName("userToken")
    val userToken: String,
    val file: File
)

data class ItemsInfo(
    val name: String,
    val qty: Int,
    val price: Double
)

data class ItemsResponse(
    val items: Items
)

data class Items(
    val items: List<ItemsInfo>
)