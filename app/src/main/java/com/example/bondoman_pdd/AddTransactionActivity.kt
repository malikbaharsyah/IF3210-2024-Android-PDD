package com.example.bondoman_pdd

import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.bondoman_pdd.ui.addTransactions.AddTransactionFragment
import java.util.Objects

class AddTransactionActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_transaction)
        Objects.requireNonNull(getSupportActionBar())?.setBackgroundDrawable(ColorDrawable(resources.getColor(R.color.top_bg)))
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, AddTransactionFragment.newInstance())
                .commitNow()
        }
    }
}