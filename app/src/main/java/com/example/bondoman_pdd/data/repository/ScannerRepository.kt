package com.example.bondoman_pdd.data.repository

import SecureStorage.getToken
import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.bondoman_pdd.data.model.ItemsResponse
import com.example.bondoman_pdd.data.service.RetrofitClient
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ScannerRepository {
    fun scanner(context: Context, file: MultipartBody.Part): LiveData<Result<ItemsResponse>> {
        val resultLiveData = MutableLiveData<Result<ItemsResponse>>()

        val userToken = getToken(context)
        if (userToken != null) {
            RetrofitClient.scannerInstance.uploadBill("Bearer $userToken", file)
                .enqueue(object : Callback<ItemsResponse> {
                    override fun onResponse(
                        call: Call<ItemsResponse>,
                        response: Response<ItemsResponse>
                    ) {
                        if (response.isSuccessful) {
                            resultLiveData.value = Result.success(response.body()!!)
                        } else {
                            resultLiveData.value =
                                Result.failure(Throwable("Failed to upload file"))
                        }
                    }

                    override fun onFailure(call: Call<ItemsResponse>, t: Throwable) {
                        resultLiveData.value = Result.failure(t)
                    }
                })
        } else {
            resultLiveData.value = Result.failure(Throwable("Failed to retrieve user token"))
        }

        return resultLiveData
    }
}