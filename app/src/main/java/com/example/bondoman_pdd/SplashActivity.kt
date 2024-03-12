package com.example.bondoman_pdd

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            setContentView(R.layout.activity_splash)

            // Handler 3000ms
            android.os.Handler().postDelayed({
                gotoMainActivity()
            }, 3000)
        }

    private fun gotoMainActivity(){
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }
}