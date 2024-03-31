package com.example.bondoman_pdd.ui.scanner

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bondoman_pdd.data.model.ItemsResponse
import com.example.bondoman_pdd.data.model.LoginResponse
import com.example.bondoman_pdd.data.repository.LoginRepository
import com.example.bondoman_pdd.data.repository.ScannerRepository
import kotlinx.coroutines.launch
import okhttp3.MultipartBody
import java.io.File

class ScannerViewModel(private val scannerRepository: ScannerRepository) : ViewModel() {

    private val _scanResult = MutableLiveData<Result<ItemsResponse>>()
    val loginResult: LiveData<Result<ItemsResponse>> = _scanResult

    fun scan(userToken: Context, file: MultipartBody.Part) {
        viewModelScope.launch {
            val resultLiveData = scannerRepository.scanner(userToken, file)
            resultLiveData.observeForever { result ->
                _scanResult.postValue(result)
            }
        }
    }
}