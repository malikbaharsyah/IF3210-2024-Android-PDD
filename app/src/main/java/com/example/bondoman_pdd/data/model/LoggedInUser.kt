package com.example.bondoman_pdd.data.model

// Menyimpan data user yang sedang login ke aplikasi static

data class LoggedInUser(
    val userId: String,
    val displayName: String
)