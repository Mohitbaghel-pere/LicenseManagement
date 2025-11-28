package com.licensemanagement.app.data.repository

import com.licensemanagement.app.data.api.ApiService
import com.licensemanagement.app.data.model.*
import javax.inject.Inject

class PackRepository @Inject constructor(
    private val apiService: ApiService,
    private val preferencesManager: PreferencesManager
) {
    suspend fun listPacks(page: Int = 1, limit: Int = 10): Result<SubscriptionPackListResponse> {
        return try {
            val token = preferencesManager.getToken()
            if (token == null) {
                return Result.failure(Exception("Not authenticated"))
            }
            val response = apiService.listSubscriptionPacks("Bearer $token", page, limit)
            if (response.isSuccessful && response.body() != null) {
                Result.success(response.body()!!)
            } else {
                Result.failure(Exception("Failed to fetch packs: ${response.message()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun getPack(packId: Int): Result<SubscriptionPack> {
        return try {
            val token = preferencesManager.getToken()
            if (token == null) {
                return Result.failure(Exception("Not authenticated"))
            }
            val response = apiService.getSubscriptionPack("Bearer $token", packId)
            if (response.isSuccessful && response.body() != null) {
                Result.success(response.body()!!.pack)
            } else {
                Result.failure(Exception("Failed to fetch pack: ${response.message()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun createPack(
        name: String,
        description: String,
        sku: String,
        price: Double,
        validityMonths: Int
    ): Result<SubscriptionPack> {
        return try {
            val token = preferencesManager.getToken()
            if (token == null) {
                return Result.failure(Exception("Not authenticated"))
            }
            val response = apiService.createSubscriptionPack(
                "Bearer $token",
                SubscriptionPackCreateRequest(name, description, sku, price, validityMonths)
            )
            if (response.isSuccessful && response.body() != null) {
                Result.success(response.body()!!.pack)
            } else {
                Result.failure(Exception("Failed to create pack: ${response.message()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun updatePack(
        packId: Int,
        name: String?,
        description: String?,
        sku: String?,
        price: Double?,
        validityMonths: Int?
    ): Result<SubscriptionPack> {
        return try {
            val token = preferencesManager.getToken()
            if (token == null) {
                return Result.failure(Exception("Not authenticated"))
            }
            val response = apiService.updateSubscriptionPack(
                "Bearer $token",
                packId,
                SubscriptionPackUpdateRequest(name, description, sku, price, validityMonths)
            )
            if (response.isSuccessful && response.body() != null) {
                Result.success(response.body()!!.pack)
            } else {
                Result.failure(Exception("Failed to update pack: ${response.message()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun deletePack(packId: Int): Result<Boolean> {
        return try {
            val token = preferencesManager.getToken()
            if (token == null) {
                return Result.failure(Exception("Not authenticated"))
            }
            val response = apiService.deleteSubscriptionPack("Bearer $token", packId)
            if (response.isSuccessful && response.body() != null) {
                Result.success(response.body()!!.success)
            } else {
                Result.failure(Exception("Failed to delete pack: ${response.message()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}

