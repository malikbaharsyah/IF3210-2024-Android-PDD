package com.example.bondoman_pdd.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.bondoman_pdd.data.model.LoginRequest
import com.example.bondoman_pdd.data.model.LoginResponse
import com.example.bondoman_pdd.data.service.RetrofitClient
import retrofit2.Callback
import android.util.Log

class LoginRepository {

    fun login(email: String, password: String): LiveData<Result<LoginResponse>> {
        val loginResult = MutableLiveData<Result<LoginResponse>>()

        RetrofitClient.loginInstace.loginUser(LoginRequest(email, password)).enqueue(object : Callback<LoginResponse> {
            override fun onResponse(call: retrofit2.Call<LoginResponse>, response: retrofit2.Response<LoginResponse>) {
                if (response.isSuccessful) {
                    Log.d("LoginRepository", "Login success: ${response.body()}")
                    loginResult.postValue(Result.success(response.body()!!))
                } else {
                    Log.d("LoginRepository", "Login failed with code: ${response.code()}")
                    loginResult.postValue(Result.failure(RuntimeException("Login failed")))
                }
            }

            override fun onFailure(call: retrofit2.Call<LoginResponse>, t: Throwable) {
                Log.d("LoginRepository", "Login error", t)
                loginResult.postValue(Result.failure(t))
            }

        })
        return loginResult
    } 
}

