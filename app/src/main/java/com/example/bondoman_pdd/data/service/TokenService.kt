package com.example.bondoman_pdd.data.service

import com.example.bondoman_pdd.data.model.TokenResponse
import retrofit2.Call
import retrofit2.http.Header
import retrofit2.http.POST

interface TokenService {
    @POST("api/auth/token")
    fun checkToken(@Header("Authorization") authToken: String): Call<TokenResponse>
}