package com.example.bondoman_pdd.data.service

import com.example.bondoman_pdd.data.model.ScannerResponse
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface ScannerService {
    @Multipart
    @POST("api/bill/upload")
    fun uploadBill(
        @Header("Authorization") userToken: String,
        @Part file: MultipartBody.Part
    ): Call<ScannerResponse>
}