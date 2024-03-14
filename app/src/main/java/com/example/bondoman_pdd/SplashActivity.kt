package com.example.bondoman_pdd

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.bondoman_pdd.ui.login.LoginActivity

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            setContentView(R.layout.activity_splash)

            // Handler 3000ms
            android.os.Handler().postDelayed({
                gotoLoginActivity()
            }, 1000)
        }

    private fun gotoLoginActivity(){
        startActivity(Intent(this, LoginActivity::class.java))
        finish()
    }
}