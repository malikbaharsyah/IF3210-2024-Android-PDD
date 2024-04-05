package com.example.bondoman_pdd.data.service

import com.example.bondoman_pdd.data.model.LoginRequest
import com.example.bondoman_pdd.data.model.LoginResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface LoginService {
    @POST("api/auth/login")
    fun loginUser(@Body loginRequest: LoginRequest): Call<LoginResponse>
}
