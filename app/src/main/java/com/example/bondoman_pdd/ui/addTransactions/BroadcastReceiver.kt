package com.example.bondoman_pdd.ui.addTransactions

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import com.example.bondoman_pdd.databinding.FragmentAddTransactionBinding
import com.example.bondoman_pdd.ui.settings.ACTION_RANDOMIZE_TRANSACTION
import com.google.android.material.snackbar.Snackbar

private const val TAG = "MyBroadcastReceiver"

class MyBroadcastReceiver(private val binding: FragmentAddTransactionBinding) : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        val action = intent.action
        if (action != null) {
            when (action) {
                // Define your action here, for example:
                ACTION_RANDOMIZE_TRANSACTION -> {
                    print("aktivasi receiver")
                    val log = buildLogMessage(intent)
                    Log.d(TAG, log)
                    Snackbar.make(binding.root, log, Snackbar.LENGTH_LONG).show()

                    // Add your additional condition here
                    val extraData = intent.getStringExtra("extra_data")
                    if (extraData != null) {
                        Log.d(TAG, "Extra Data: $extraData")
                    }
                }
                // Add more cases if needed
            }
        }
    }

    private fun buildLogMessage(intent: Intent): String {
        return StringBuilder().apply {
            append("Action: ${intent.action}\n")
            append("URI: ${intent.toUri(Intent.URI_INTENT_SCHEME)}\n")
        }.toString()
    }
}
