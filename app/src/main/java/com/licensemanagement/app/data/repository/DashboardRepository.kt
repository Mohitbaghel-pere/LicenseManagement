package com.licensemanagement.app.data.repository

import com.licensemanagement.app.data.api.ApiService
import com.licensemanagement.app.data.model.DashboardResponse
import javax.inject.Inject

class DashboardRepository @Inject constructor(
    private val apiService: ApiService,
    private val preferencesManager: PreferencesManager
) {
    suspend fun getDashboard(): Result<DashboardResponse> {
        return try {
            val token = preferencesManager.getToken()
            if (token == null) {
                return Result.failure(Exception("Not authenticated"))
            }
            val response = apiService.getDashboard("Bearer $token")
            if (response.isSuccessful && response.body() != null) {
                Result.success(response.body()!!)
            } else {
                Result.failure(Exception("Failed to fetch dashboard: ${response.message()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}

