package com.example.bondoman_pdd.data.service

import SecureStorage
import android.app.IntentService
import android.content.Intent
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.example.bondoman_pdd.data.repository.TokenRepository

class TokenCheckService : IntentService("TokenCheckService") {
    companion object {
        const val ACTION_PROCESS_UPDATE = "action.PROCESS_UPDATE"
        const val EXTRA_IS_SUCCESSFUL = "extra.IS_SUCCESSFUL"
    }

    @Deprecated("Deprecated in Java")
    override fun onHandleIntent(intent: Intent?) {
        val token = SecureStorage.getToken(this)

        // Initialize your TokenRepository (consider dependency injection for a cleaner approach)
        val tokenRepository = TokenRepository()

        // Use the repository to check the token
        if (token != null) {
            tokenRepository.checkToken(token) { isValid ->
                // Create and send a broadcast based on the token validity
                val broadcastIntent = Intent(ACTION_PROCESS_UPDATE).apply {
                    putExtra(EXTRA_IS_SUCCESSFUL, isValid)
                }
                LocalBroadcastManager.getInstance(this).sendBroadcast(broadcastIntent)
            }
        }
    }
}