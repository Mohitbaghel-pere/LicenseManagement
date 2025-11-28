package com.licensemanagement.app.data.repository

import com.licensemanagement.app.data.api.ApiClient
import com.licensemanagement.app.data.model.*

class SubscriptionPackRepository {
    private val apiService = ApiClient.apiService

    suspend fun getSubscriptionPacks(
        token: String,
        page: Int = 1,
        limit: Int = 10
    ): Result<SubscriptionPackListResponse> {
        return try {
            val response = apiService.getSubscriptionPacks("Bearer $token", page, limit)
            if (response.isSuccessful && response.body() != null) {
                Result.success(response.body()!!)
            } else {
                Result.failure(Exception(response.message()))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun createSubscriptionPack(
        token: String,
        request: SubscriptionPackCreateRequest
    ): Result<ApiResponse<SubscriptionPack>> {
        return try {
            val response = apiService.createSubscriptionPack("Bearer $token", request)
            if (response.isSuccessful && response.body() != null) {
                Result.success(response.body()!!)
            } else {
                Result.failure(Exception(response.message()))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun updateSubscriptionPack(
        token: String,
        packId: Int,
        request: SubscriptionPackCreateRequest
    ): Result<ApiResponse<SubscriptionPack>> {
        return try {
            val response = apiService.updateSubscriptionPack("Bearer $token", packId, request)
            if (response.isSuccessful && response.body() != null) {
                Result.success(response.body()!!)
            } else {
                Result.failure(Exception(response.message()))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun deleteSubscriptionPack(token: String, packId: Int): Result<ErrorResponse> {
        return try {
            val response = apiService.deleteSubscriptionPack("Bearer $token", packId)
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

