package com.example.bondoman_pdd.data.repository

import com.example.bondoman_pdd.data.model.TokenResponse
import com.example.bondoman_pdd.data.service.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TokenRepository {

    fun checkToken(token: String, callback: (Boolean) -> Unit) {
        RetrofitClient.tokenInstance.checkToken("Bearer $token").enqueue(object : Callback<TokenResponse> {
            override fun onResponse(call: Call<TokenResponse>, response: Response<TokenResponse>) {
                if (response.isSuccessful) {
                    // Assuming the response body directly indicates token validity
                    callback(true)
                } else {
                    callback(false)
                }
            }

            override fun onFailure(call: Call<TokenResponse>, t: Throwable) {
                callback(false)
            }
        })
    }

}