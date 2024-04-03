package com.example.bondoman_pdd.ui.addTransactions

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.provider.Settings
import android.widget.Toast
import com.example.bondoman_pdd.databinding.ActivityAddTransactionBinding
import com.example.bondoman_pdd.ui.settings.ACTION_RANDOMIZE_TRANSACTION
import com.google.android.material.snackbar.Snackbar

private const val TAG = "MyBroadcastReceiver"

class MyBroadcastReceiver(private val binding: ActivityAddTransactionBinding) : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        println("BroadcastReceiver received intent with action: ${intent.action}")
        val action = intent.action
        if (action != null) {
            println("BroadcastReceiver received intent with action: $action")
            when (action) {
                (if (action == ACTION_RANDOMIZE_TRANSACTION) {
                    // Handle the randomize transaction action
                    println("Randomize transaction action received")
                    Toast.makeText(context, "Randomize transaction action received", Toast.LENGTH_SHORT).show()
                    // Change activity judul to autofill
                    binding.judul.setText("Makan Siang")
                } else if (action == Intent.ACTION_AIRPLANE_MODE_CHANGED) {
                    val isTurnedOn = Settings.Global.getInt(context.contentResolver, Settings.Global.AIRPLANE_MODE_ON, 0) != 0
                    if (isTurnedOn) {
                        println("Airplane mode is turned on")
                        Toast.makeText(context, "Airplane mode is turned on", Toast.LENGTH_SHORT).show()
                    } else {
                        println("Airplane mode is turned off")
                        Toast.makeText(context, "Airplane mode is turned off", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    println("Unknown action received")
                }).toString() -> {
                    // Add more cases if needed
                }
                // Add more cases if needed
            }
        } else {
            println("BroadcastReceiver received intent with no action")
        }
    }

    private fun buildLogMessage(intent: Intent): String {
        return StringBuilder().apply {
            append("Action: ${intent.action}\n")
            append("URI: ${intent.toUri(Intent.URI_INTENT_SCHEME)}\n")
        }.toString()
    }
}
