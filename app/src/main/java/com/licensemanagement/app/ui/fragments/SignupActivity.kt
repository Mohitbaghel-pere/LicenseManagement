package com.licensemanagement.app.ui.auth

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.licensemanagement.app.R
import com.licensemanagement.app.databinding.ActivitySignupBinding
import com.licensemanagement.app.ui.main.MainActivity
import com.licensemanagement.app.util.PreferencesManager
import com.licensemanagement.app.viewmodel.AuthViewModel
import kotlinx.coroutines.launch

class SignupActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignupBinding
    private val viewModel: AuthViewModel by viewModels()
    private lateinit var preferencesManager: PreferencesManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignupBinding.inflate(layoutInflater)
        setContentView(binding.root)

        preferencesManager = PreferencesManager(this)

        setupClickListeners()
        observeViewModel()
    }

    private fun setupClickListeners() {
        binding.btnSignUp.setOnClickListener {
            val name = binding.etName.text.toString().trim()
            val email = binding.etEmail.text.toString().trim()
            val password = binding.etPassword.text.toString().trim()
            val phone = binding.etPhone.text.toString().trim()

            if (validateInput(name, email, password, phone)) {
                viewModel.signup(name, email, password, phone)
            }
        }
    }

    private fun validateInput(name: String, email: String, password: String, phone: String): Boolean {
        if (name.isEmpty()) {
            binding.etName.error = "Name is required"
            return false
        }
        if (email.isEmpty()) {
            binding.etEmail.error = "Email is required"
            return false
        }
        if (password.isEmpty() || password.length < 6) {
            binding.etPassword.error = "Password must be at least 6 characters"
            return false
        }
        if (phone.isEmpty()) {
            binding.etPhone.error = "Phone is required"
            return false
        }
        return true
    }

    private fun observeViewModel() {
        lifecycleScope.launch {
            viewModel.loginState.collect { state ->
                when (state) {
                    is AuthViewModel.LoginState.Loading -> {
                        binding.btnSignUp.isEnabled = false
                        binding.progressBar.visibility = android.view.View.VISIBLE
                    }
                    is AuthViewModel.LoginState.Success -> {
                        binding.progressBar.visibility = android.view.View.GONE
                        preferencesManager.saveToken(state.token)
                        preferencesManager.saveUserEmail(state.email ?: "")
                        preferencesManager.saveUserName(state.name ?: "")
                        preferencesManager.saveUserRole("customer")
                        preferencesManager.setLoggedIn(true)
                        navigateToMain()
                    }
                    is AuthViewModel.LoginState.Error -> {
                        binding.progressBar.visibility = android.view.View.GONE
                        binding.btnSignUp.isEnabled = true
                        Toast.makeText(this@SignupActivity, state.message, Toast.LENGTH_SHORT).show()
                    }
                    is AuthViewModel.LoginState.Idle -> {
                        binding.progressBar.visibility = android.view.View.GONE
                        binding.btnSignUp.isEnabled = true
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

