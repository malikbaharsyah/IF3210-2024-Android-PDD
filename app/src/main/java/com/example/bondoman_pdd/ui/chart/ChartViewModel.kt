package com.example.bondoman_pdd.ui.chart

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ChartViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is chart Fragment"
    }
    val text: LiveData<String> = _text
}