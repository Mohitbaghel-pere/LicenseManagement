package com.licensemanagement.app.data.repository

import com.licensemanagement.app.data.api.ApiClient
import com.licensemanagement.app.data.model.*

class SubscriptionRepository {
    private val apiService = ApiClient.apiService

    suspend fun getCurrentSubscription(token: String): Result<CurrentSubscriptionResponse> {
        return try {
            val response = apiService.getCurrentSubscription("Bearer $token")
            if (response.isSuccessful && response.body() != null) {
                Result.success(response.body()!!)
            } else {
                Result.failure(Exception(response.message()))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun requestSubscription(token: String, sku: String): Result<SubscriptionCreateResponse> {
        return try {
            val response = apiService.requestSubscription("Bearer $token", SubscriptionRequest(sku = sku))
            if (response.isSuccessful && response.body() != null) {
                Result.success(response.body()!!)
            } else {
                Result.failure(Exception(response.message()))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun deactivateSubscription(token: String): Result<ErrorResponse> {
        return try {
            val response = apiService.deactivateSubscription("Bearer $token")
            if (response.isSuccessful && response.body() != null) {
                Result.success(response.body()!!)
            } else {
                Result.failure(Exception(response.message()))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun getSubscriptionHistory(
        token: String,
        page: Int = 1,
        limit: Int = 10,
        sort: String = "desc"
    ): Result<SubscriptionHistoryResponse> {
        return try {
            val response = apiService.getSubscriptionHistory("Bearer $token", page, limit, sort)
            if (response.isSuccessful && response.body() != null) {
                Result.success(response.body()!!)
            } else {
                Result.failure(Exception(response.message()))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    // SDK methods
    suspend fun getSDKSubscription(apiKey: String): Result<CurrentSubscriptionResponse> {
        return try {
            val response = apiService.getSDKSubscription(apiKey)
            if (response.isSuccessful && response.body() != null) {
                Result.success(response.body()!!)
            } else {
                Result.failure(Exception(response.message()))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun requestSDKSubscription(apiKey: String, packSku: String): Result<SubscriptionCreateResponse> {
        return try {
            val response = apiService.requestSDKSubscription(apiKey, mapOf("pack_sku" to packSku))
            if (response.isSuccessful && response.body() != null) {
                Result.success(response.body()!!)
            } else {
                Result.failure(Exception(response.message()))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun deactivateSDKSubscription(apiKey: String): Result<ErrorResponse> {
        return try {
            val response = apiService.deactivateSDKSubscription(apiKey)
            if (response.isSuccessful && response.body() != null) {
                Result.success(response.body()!!)
            } else {
                Result.failure(Exception(response.message()))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun getSDKSubscriptionHistory(
        apiKey: String,
        page: Int = 1,
        limit: Int = 10,
        sort: String = "desc"
    ): Result<SubscriptionHistoryResponse> {
        return try {
            val response = apiService.getSDKSubscriptionHistory(apiKey, page, limit, sort)
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
