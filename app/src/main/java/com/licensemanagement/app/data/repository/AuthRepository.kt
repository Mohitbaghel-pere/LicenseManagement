package com.licensemanagement.app.data.repository

import com.licensemanagement.app.data.api.ApiClient
import com.licensemanagement.app.data.model.LoginRequest
import com.licensemanagement.app.data.model.LoginResponse
import com.licensemanagement.app.data.model.SDKAuthResponse
import com.licensemanagement.app.data.model.SignupRequest

class AuthRepository {
    private val apiService = ApiClient.apiService

    suspend fun adminLogin(email: String, password: String): Result<LoginResponse> {
        return try {
            val response = apiService.adminLogin(LoginRequest(email, password))
            if (response.isSuccessful && response.body() != null) {
                Result.success(response.body()!!)
            } else {
                val errorBody = response.errorBody()?.string() ?: response.message()
                Result.failure(Exception(errorBody))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun customerLogin(email: String, password: String): Result<LoginResponse> {
        return try {
            val response = apiService.customerLogin(LoginRequest(email, password))
            if (response.isSuccessful && response.body() != null) {
                Result.success(response.body()!!)
            } else {
                Result.failure(Exception(response.message()))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun customerSignup(name: String, email: String, password: String, phone: String): Result<LoginResponse> {
        return try {
            val response = apiService.customerSignup(SignupRequest(name, email, password, phone))
            if (response.isSuccessful && response.body() != null) {
                Result.success(response.body()!!)
            } else {
                Result.failure(Exception(response.message()))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun sdkLogin(email: String, password: String): Result<SDKAuthResponse> {
        return try {
            val response = apiService.sdkLogin(LoginRequest(email, password))
            if (response.isSuccessful && response.body() != null) {
                Result.success(response.body()!!)
            } else {
                Result.failure(Exception(response.message()))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
