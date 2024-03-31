package com.example.bondoman_pdd

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.bondoman_pdd.ui.login.LoginActivity

@SuppressLint("CustomSplashScreen")
class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Check if the token exists
        val token = SecureStorage.getToken(this)

        if (token.isNullOrEmpty()) {
            // No token found, start LoginActivity
            startActivity(Intent(this, LoginActivity::class.java))
        } else {
            // Token found, start MainActivity
            startActivity(Intent(this, MainActivity::class.java))
        }

        finish() // Close SplashActivity
    }
}
