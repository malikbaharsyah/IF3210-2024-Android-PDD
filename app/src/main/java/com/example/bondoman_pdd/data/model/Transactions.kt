package com.example.bondoman_pdd.data.model
data class Transactions(
    val id : Int,
    val nim : Int,
    val judul : String,
    val nominal : Float,
    val kategori : String,
    val tanggal : String,
    val lokasi : String,
)
