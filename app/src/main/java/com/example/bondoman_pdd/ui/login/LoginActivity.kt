package com.example.bondoman_pdd.ui.login

import SecureStorage
import android.app.Activity
import android.content.Intent
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.EditText
import android.widget.Toast
import com.example.bondoman_pdd.MainActivity
import com.example.bondoman_pdd.databinding.ActivityLoginBinding

import com.example.bondoman_pdd.R
import com.example.bondoman_pdd.data.repository.LoginRepository

class LoginActivity : AppCompatActivity() {

    private lateinit var loginViewModel: LoginViewModel
    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val username = binding.username
        val password = binding.password
        val loginButton = binding.login
        val loadingProgressBar = binding.loading

        val loginRepository = LoginRepository()
        val viewModelFactory = LoginViewModelFactory(loginRepository)
        loginViewModel = ViewModelProvider(this, viewModelFactory)
            .get(LoginViewModel::class.java)


        loginViewModel.loginFormState.observe(this@LoginActivity, Observer {
            val loginState = it ?: return@Observer

            // disable login button unless both username / password is valid
            loginButton.isEnabled = loginState.isDataValid

            if (loginState.usernameError != null) {
                username.error = getString(loginState.usernameError)
            }
            if (loginState.passwordError != null) {
                password.error = getString(loginState.passwordError)
            }
        })

        loginViewModel.loginResult.observe(this@LoginActivity, Observer { result ->
            val email = username.text.toString()
            if (loadingProgressBar != null) {
                loadingProgressBar.visibility = View.GONE
            }
            result.fold(onSuccess = { response ->
                // store token
                SecureStorage.storeToken(this@LoginActivity, response.token)
                // Handle successful login
                val welcomeMessage = getString(R.string.welcome) + email
                Toast.makeText(applicationContext, welcomeMessage, Toast.LENGTH_LONG).show()
                // Proceed to MainActivity
                startActivity(Intent(this, MainActivity::class.java))
                setResult(Activity.RESULT_OK)
                finish()
            }, onFailure = {
                // Handle login failure
                showLoginFailed(R.string.login_failed)
                return@Observer
            })
        })

        username.afterTextChanged {
            loginViewModel.loginDataChanged(
                username.text.toString(),
                password.text.toString()
            )
        }

        password.apply {
            afterTextChanged {
                loginViewModel.loginDataChanged(
                    username.text.toString(),
                    password.text.toString()
                )
            }

            loginButton.setOnClickListener {
                if (loadingProgressBar != null) {
                    loadingProgressBar.visibility = View.VISIBLE
                }
                val email = username.text.toString()
                val pw = password.text.toString()
                loginViewModel.login(email, pw)
            }
        }
    }

    private fun showLoginFailed(@StringRes errorString: Int) {
        Toast.makeText(applicationContext, errorString, Toast.LENGTH_SHORT).show()
    }
}

/**
 * Extension function to simplify setting an afterTextChanged action to EditText components.
 */
fun EditText.afterTextChanged(afterTextChanged: (String) -> Unit) {
    this.addTextChangedListener(object : TextWatcher {
        override fun afterTextChanged(editable: Editable?) {
            afterTextChanged.invoke(editable.toString())
        }

        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
    })
}