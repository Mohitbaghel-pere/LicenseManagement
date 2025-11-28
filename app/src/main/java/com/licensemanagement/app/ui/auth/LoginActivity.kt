package com.licensemanagement.app.ui.auth

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.licensemanagement.app.R
import com.licensemanagement.app.databinding.ActivityLoginBinding
import com.licensemanagement.app.ui.main.MainActivity
import com.licensemanagement.app.util.PreferencesManager
import com.licensemanagement.app.viewmodel.AuthViewModel
import kotlinx.coroutines.launch

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private val viewModel: AuthViewModel by viewModels()
    private lateinit var preferencesManager: PreferencesManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        preferencesManager = PreferencesManager(this)

        // Check if already logged in
        if (preferencesManager.isLoggedIn()) {
            navigateToMain()
            return
        }

        setupClickListeners()
        observeViewModel()
    }

    private fun setupClickListeners() {
        binding.btnLogin.setOnClickListener {
            val email = binding.etEmail.text.toString().trim()
            val password = binding.etPassword.text.toString().trim()

            if (validateInput(email, password)) {
                viewModel.login(email, password, isAdmin = false)
            }
        }

        binding.btnAdminLogin.setOnClickListener {
            val email = binding.etEmail.text.toString().trim()
            val password = binding.etPassword.text.toString().trim()

            if (validateInput(email, password)) {
                viewModel.login(email, password, isAdmin = true)
            }
        }

        binding.tvSignUp.setOnClickListener {
            startActivity(Intent(this, SignupActivity::class.java))
        }
    }

    private fun validateInput(email: String, password: String): Boolean {
        if (email.isEmpty()) {
            binding.etEmail.error = "Email is required"
            return false
        }
        if (password.isEmpty()) {
            binding.etPassword.error = "Password is required"
            return false
        }
        return true
    }

    private fun observeViewModel() {
        lifecycleScope.launch {
            viewModel.loginState.collect { state ->
                when (state) {
                    is AuthViewModel.LoginState.Loading -> {
                        binding.btnLogin.isEnabled = false
                        binding.progressBar.visibility = android.view.View.VISIBLE
                    }
                    is AuthViewModel.LoginState.Success -> {
                        binding.progressBar.visibility = android.view.View.GONE
                        preferencesManager.saveToken(state.token)
                        preferencesManager.saveUserEmail(state.email ?: "")
                        preferencesManager.saveUserName(state.name ?: "")
                        preferencesManager.saveUserRole(if (state.isAdmin) "admin" else "customer")
                        preferencesManager.setLoggedIn(true)
                        navigateToMain()
                    }
                    is AuthViewModel.LoginState.Error -> {
                        binding.progressBar.visibility = android.view.View.GONE
                        binding.btnLogin.isEnabled = true
                        Toast.makeText(this@LoginActivity, state.message, Toast.LENGTH_SHORT).show()
                    }
                    is AuthViewModel.LoginState.Idle -> {
                        binding.progressBar.visibility = android.view.View.GONE
                        binding.btnLogin.isEnabled = true
                    }
                }
            }
        }
    }

    private fun navigateToMain() {
        val intent = Intent(this, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
        finish()
    }
}

