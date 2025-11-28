package com.licensemanagement.app.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.licensemanagement.app.data.repository.AuthRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class AuthViewModel : ViewModel() {
    private val authRepository = AuthRepository()

    sealed class LoginState {
        object Idle : LoginState()
        object Loading : LoginState()
        data class Success(
            val token: String,
            val email: String?,
            val name: String?,
            val isAdmin: Boolean
        ) : LoginState()
        data class Error(val message: String) : LoginState()
    }

    private val _loginState = MutableStateFlow<LoginState>(LoginState.Idle)
    val loginState: StateFlow<LoginState> = _loginState

    fun login(email: String, password: String, isAdmin: Boolean) {
        viewModelScope.launch {
            _loginState.value = LoginState.Loading
            val result = if (isAdmin) {
                authRepository.adminLogin(email, password)
            } else {
                authRepository.customerLogin(email, password)
            }

            result.onSuccess { response ->
                if (response.success && response.token != null) {
                    _loginState.value = LoginState.Success(
                        token = response.token,
                        email = response.email,
                        name = response.name,
                        isAdmin = isAdmin
                    )
                } else {
                    _loginState.value = LoginState.Error("Login failed")
                }
            }.onFailure { exception ->
                _loginState.value = LoginState.Error(exception.message ?: "Login failed")
            }
        }
    }

    fun signup(name: String, email: String, password: String, phone: String) {
        viewModelScope.launch {
            _loginState.value = LoginState.Loading
            val result = authRepository.customerSignup(name, email, password, phone)

            result.getOrElse { exception ->
                _loginState.value = LoginState.Error(exception.message ?: "Signup failed")
                return@launch
            }.let { response ->
                if (response.success && response.token != null) {
                    _loginState.value = LoginState.Success(
                        token = response.token,
                        email = response.email,
                        name = response.name,
                        isAdmin = false
                    )
                } else {
                    _loginState.value = LoginState.Error("Signup failed")
                }
            }
        }
    }
}

