package com.example.bondoman_pdd

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.bondoman_pdd.ui.main.AddTransactionFragment

class AddTransactionActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_transaction)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, AddTransactionFragment.newInstance())
                .commitNow()
        }
    }
}