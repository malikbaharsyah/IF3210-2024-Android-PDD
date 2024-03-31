package com.example.bondoman_pdd

import SecureStorage
import android.app.AlarmManager
import android.app.AlertDialog
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import androidx.core.content.ContextCompat
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.bondoman_pdd.data.service.TokenCheckService
import com.example.bondoman_pdd.databinding.ActivityMainBinding
import com.example.bondoman_pdd.data.utils.NetworkUtils
import com.example.bondoman_pdd.ui.login.LoginActivity
import java.util.Objects

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var alarmManager: AlarmManager
    private lateinit var pendingIntent: PendingIntent

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        Objects.requireNonNull(getSupportActionBar())?.setBackgroundDrawable(ColorDrawable(resources.getColor(R.color.top_bg)))

        val navView: BottomNavigationView = binding.navView

        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_transactions, R.id.navigation_scanner, R.id.navigation_chart, R.id.navigation_settings
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

    }

    private fun logout() {
        // Clear the token and other logout logic
        SecureStorage.deleteToken(this)
        SecureStorage.deleteEmail(this)
        startActivity(Intent(this, LoginActivity::class.java))
        finish()
    }

    override fun onResume() {
        super.onResume()
        if (!NetworkUtils.isOnline(this)) {
            NetworkUtils.showNoInternetConnectionPopup(this)
        }
        scheduleTokenCheck()
        // Register BroadcastReceiver to receive the service's messages
        LocalBroadcastManager.getInstance(this).registerReceiver(
            TokenCheckReceiver { result ->
                if (!result) {
                    AlertDialog.Builder(this@MainActivity).apply {
                        setTitle("Session Expired")
                        setMessage("Your session has expired. Please log in again.")
                        setPositiveButton("OK") { _, _ ->
                            logout() // Call the logout method when the "OK" button is pressed
                        }
                        setCancelable(false) // Prevent the dialog from being dismissed without user action
                        show() // Show the dialog to the user
                    }
                } else {
                    Log.d("MainActivity", "Token still valid")
                }
            },
            IntentFilter(TokenCheckService.ACTION_PROCESS_UPDATE)
        )
    }

    override fun onStop() {
        super.onStop()
        alarmManager.cancel(pendingIntent)
        LocalBroadcastManager.getInstance(this).unregisterReceiver(TokenCheckReceiver { })
    }

    private fun scheduleTokenCheck() {
        val intent = Intent(applicationContext, TokenCheckService::class.java)
        // Use FLAG_IMMUTABLE for PendingIntent if it does not need to be altered
        val flags =
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        pendingIntent = PendingIntent.getService(applicationContext, 0, intent, flags)

        alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val interval = 60000L // 1 minute in milliseconds

        alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), interval, pendingIntent)
    }

}

class TokenCheckReceiver(private val callback: (Boolean) -> Unit) : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        intent?.getBooleanExtra(TokenCheckService.EXTRA_IS_SUCCESSFUL, false)?.let { result ->
            callback(result)
        }
    }
}