package com.licensemanagement.app.data.repository

import com.licensemanagement.app.data.api.ApiService
import com.licensemanagement.app.data.model.*
import javax.inject.Inject

class CustomerRepository @Inject constructor(
    private val apiService: ApiService,
    private val preferencesManager: PreferencesManager
) {
    suspend fun listCustomers(page: Int = 1, limit: Int = 10, search: String? = null): Result<CustomerListResponse> {
        return try {
            val token = preferencesManager.getToken()
            if (token == null) {
                return Result.failure(Exception("Not authenticated"))
            }
            val response = apiService.listCustomers("Bearer $token", page, limit, search)
            if (response.isSuccessful && response.body() != null) {
                Result.success(response.body()!!)
            } else {
                Result.failure(Exception("Failed to fetch customers: ${response.message()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun getCustomer(customerId: Int): Result<Customer> {
        return try {
            val token = preferencesManager.getToken()
            if (token == null) {
                return Result.failure(Exception("Not authenticated"))
            }
            val response = apiService.getCustomer("Bearer $token", customerId)
            if (response.isSuccessful && response.body() != null) {
                Result.success(response.body()!!.customer)
            } else {
                Result.failure(Exception("Failed to fetch customer: ${response.message()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun createCustomer(name: String, email: String, phone: String): Result<Customer> {
        return try {
            val token = preferencesManager.getToken()
            if (token == null) {
                return Result.failure(Exception("Not authenticated"))
            }
            val response = apiService.createCustomer("Bearer $token", CustomerCreateRequest(name, email, phone))
            if (response.isSuccessful && response.body() != null) {
                Result.success(response.body()!!.customer)
            } else {
                Result.failure(Exception("Failed to create customer: ${response.message()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun updateCustomer(customerId: Int, name: String?, phone: String?): Result<Customer> {
        return try {
            val token = preferencesManager.getToken()
            if (token == null) {
                return Result.failure(Exception("Not authenticated"))
            }
            val response = apiService.updateCustomer("Bearer $token", customerId, CustomerUpdateRequest(name, phone))
            if (response.isSuccessful && response.body() != null) {
                Result.success(response.body()!!.customer)
            } else {
                Result.failure(Exception("Failed to update customer: ${response.message()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun deleteCustomer(customerId: Int): Result<Boolean> {
        return try {
            val token = preferencesManager.getToken()
            if (token == null) {
                return Result.failure(Exception("Not authenticated"))
            }
            val response = apiService.deleteCustomer("Bearer $token", customerId)
            if (response.isSuccessful && response.body() != null) {
                Result.success(response.body()!!.success)
            } else {
                Result.failure(Exception("Failed to delete customer: ${response.message()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}

