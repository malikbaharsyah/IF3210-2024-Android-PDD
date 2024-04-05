package com.example.bondoman_pdd.data.repository

import android.graphics.Bitmap
import com.example.bondoman_pdd.data.model.ScannerResponse
import com.example.bondoman_pdd.data.service.RetrofitClient
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.ByteArrayOutputStream

class ScannerRepository {

    fun uploadBill(bitmap: Bitmap, userToken: String, callback: (ScannerResponse?, Throwable?) -> Unit) {
        // Prepare the image file to be uploaded
        val byteArrayOutputStream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream)
        val requestBody = byteArrayOutputStream.toByteArray()
            .toRequestBody("image/jpeg".toMediaTypeOrNull())
        val filePart = MultipartBody.Part.createFormData("file", "file.jpg", requestBody)

        // Make the API call using Retrofit
        RetrofitClient.scannerInstance.uploadBill("Bearer $userToken", filePart).enqueue(object :
            Callback<ScannerResponse> {
            override fun onResponse(call: Call<ScannerResponse>, response: Response<ScannerResponse>) {
                if (response.isSuccessful) {
                    // Callback with the response body
                    callback(response.body(), null)
                } else {
                    // Callback with an error if response is not successful
                    callback(null, RuntimeException("Failed to upload image: ${response.errorBody()?.string()}"))
                }
            }

            override fun onFailure(call: Call<ScannerResponse>, t: Throwable) {
                // Callback with an error on request failure
                callback(null, t)
            }
        })
    }
}
