package com.licensemanagement.app.data.api

import com.licensemanagement.app.data.model.*
import retrofit2.Response
import retrofit2.http.*

interface ApiService {
    // Authentication
    @POST("api/admin/login")
    suspend fun adminLogin(@Body request: LoginRequest): Response<LoginResponse>

    @POST("api/customer/login")
    suspend fun customerLogin(@Body request: LoginRequest): Response<LoginResponse>

    @POST("api/customer/signup")
    suspend fun customerSignup(@Body request: SignupRequest): Response<LoginResponse>

    // SDK Authentication
    @POST("sdk/auth/login")
    suspend fun sdkLogin(@Body request: LoginRequest): Response<SDKAuthResponse>

    // Admin Dashboard
    @GET("api/v1/admin/dashboard")
    suspend fun getDashboard(@Header("Authorization") token: String): Response<DashboardResponse>

    // Customer Management (Admin)
    @GET("api/v1/admin/customers")
    suspend fun getCustomers(
        @Header("Authorization") token: String,
        @Query("page") page: Int = 1,
        @Query("limit") limit: Int = 10,
        @Query("search") search: String? = null
    ): Response<CustomerListResponse>

    @POST("api/v1/admin/customers")
    suspend fun createCustomer(
        @Header("Authorization") token: String,
        @Body request: Customer
    ): Response<ApiResponse<Customer>>

    @GET("api/v1/admin/customers/{customer_id}")
    suspend fun getCustomer(
        @Header("Authorization") token: String,
        @Path("customer_id") customerId: Int
    ): Response<ApiResponse<Customer>>

    @PUT("api/v1/admin/customers/{customer_id}")
    suspend fun updateCustomer(
        @Header("Authorization") token: String,
        @Path("customer_id") customerId: Int,
        @Body request: Map<String, String>
    ): Response<ApiResponse<Customer>>

    @DELETE("api/v1/admin/customers/{customer_id}")
    suspend fun deleteCustomer(
        @Header("Authorization") token: String,
        @Path("customer_id") customerId: Int
    ): Response<ErrorResponse>

    // Subscription Pack Management (Admin)
    @GET("api/v1/admin/subscription-packs")
    suspend fun getSubscriptionPacks(
        @Header("Authorization") token: String,
        @Query("page") page: Int = 1,
        @Query("limit") limit: Int = 10
    ): Response<SubscriptionPackListResponse>

    @POST("api/v1/admin/subscription-packs")
    suspend fun createSubscriptionPack(
        @Header("Authorization") token: String,
        @Body request: SubscriptionPackCreateRequest
    ): Response<ApiResponse<SubscriptionPack>>

    @PUT("api/v1/admin/subscription-packs/{pack_id}")
    suspend fun updateSubscriptionPack(
        @Header("Authorization") token: String,
        @Path("pack_id") packId: Int,
        @Body request: SubscriptionPackCreateRequest
    ): Response<ApiResponse<SubscriptionPack>>

    @DELETE("api/v1/admin/subscription-packs/{pack_id}")
    suspend fun deleteSubscriptionPack(
        @Header("Authorization") token: String,
        @Path("pack_id") packId: Int
    ): Response<ErrorResponse>

    // Subscription Management (Admin)
    @GET("api/v1/admin/subscriptions")
    suspend fun getSubscriptions(
        @Header("Authorization") token: String,
        @Query("page") page: Int = 1,
        @Query("limit") limit: Int = 10,
        @Query("status") status: String? = null
    ): Response<SubscriptionListResponse>

    @POST("api/v1/admin/subscriptions/{subscription_id}/approve")
    suspend fun approveSubscription(
        @Header("Authorization") token: String,
        @Path("subscription_id") subscriptionId: Int
    ): Response<ErrorResponse>

    @POST("api/v1/admin/customers/{customer_id}/assign-subscription")
    suspend fun assignSubscription(
        @Header("Authorization") token: String,
        @Path("customer_id") customerId: Int,
        @Body request: Map<String, Int>
    ): Response<ErrorResponse>

    @DELETE("api/v1/admin/customers/{customer_id}/subscription/{subscription_id}")
    suspend fun unassignSubscription(
        @Header("Authorization") token: String,
        @Path("customer_id") customerId: Int,
        @Path("subscription_id") subscriptionId: Int
    ): Response<ErrorResponse>

    // Customer Self-Service
    @GET("api/v1/customer/subscription")
    suspend fun getCurrentSubscription(@Header("Authorization") token: String): Response<CurrentSubscriptionResponse>

    @POST("api/v1/customer/subscription")
    suspend fun requestSubscription(
        @Header("Authorization") token: String,
        @Body request: SubscriptionRequest
    ): Response<SubscriptionCreateResponse>

    @DELETE("api/v1/customer/subscription")
    suspend fun deactivateSubscription(@Header("Authorization") token: String): Response<ErrorResponse>

    @GET("api/v1/customer/subscription-history")
    suspend fun getSubscriptionHistory(
        @Header("Authorization") token: String,
        @Query("page") page: Int = 1,
        @Query("limit") limit: Int = 10,
        @Query("sort") sort: String = "desc"
    ): Response<SubscriptionHistoryResponse>

    // SDK Endpoints
    @GET("sdk/v1/subscription")
    suspend fun getSDKSubscription(@Header("X-API-Key") apiKey: String): Response<CurrentSubscriptionResponse>

    @POST("sdk/v1/subscription")
    suspend fun requestSDKSubscription(
        @Header("X-API-Key") apiKey: String,
        @Body request: Map<String, String>
    ): Response<SubscriptionCreateResponse>

    @DELETE("sdk/v1/subscription")
    suspend fun deactivateSDKSubscription(@Header("X-API-Key") apiKey: String): Response<ErrorResponse>

    @GET("sdk/v1/subscription-history")
    suspend fun getSDKSubscriptionHistory(
        @Header("X-API-Key") apiKey: String,
        @Query("page") page: Int = 1,
        @Query("limit") limit: Int = 10,
        @Query("sort") sort: String = "desc"
    ): Response<SubscriptionHistoryResponse>
}
