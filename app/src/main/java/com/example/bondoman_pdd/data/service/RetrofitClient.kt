package com.example.bondoman_pdd.data.service

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {

    private const val BASE_URL = "https://pbd-backend-2024.vercel.app/"

    private val instance: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
    val loginInstace: LoginService by lazy {
        instance.create(LoginService::class.java)
    }
    val tokenInstance: TokenService by lazy {
        instance.create(TokenService::class.java)
    }
}
