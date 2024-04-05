package com.example.bondoman_pdd.ui.scanner

import android.content.Context
import android.graphics.Bitmap
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bondoman_pdd.data.model.ScannerResponse
import com.example.bondoman_pdd.data.repository.ScannerRepository
import com.example.bondoman_pdd.data.transactions.setup
import kotlinx.coroutines.launch

class ScannerViewModel : ViewModel() {
    private val scannerRepository = ScannerRepository()
    val scanResult = MutableLiveData<Result<ScannerResponse>?>()

    fun uploadImage(bitmap: Bitmap, userToken: String) {
        viewModelScope.launch {
            scannerRepository.uploadBill(bitmap, userToken) { response, error ->
                if (error != null) {
                    scanResult.postValue(Result.failure(error))
                } else if (response != null) {
                    scanResult.postValue(Result.success(response))
                }
            }
        }
    }

    fun clearResult() {
        scanResult.value = null
    }
}