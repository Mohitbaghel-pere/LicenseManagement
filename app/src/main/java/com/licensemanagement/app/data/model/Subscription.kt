package com.licensemanagement.app.data.model

import com.google.gson.annotations.SerializedName

data class Subscription(
    @SerializedName("id") val id: Int,
    @SerializedName("customer_id") val customerId: Int,
    @SerializedName("pack_id") val packId: Int,
    @SerializedName("status") val status: String,
    @SerializedName("pack_name") val packName: String?,
    @SerializedName("pack_sku") val packSku: String?,
    @SerializedName("price") val price: Double?,
    @SerializedName("validity_months") val validityMonths: Int?,
    @SerializedName("requested_at") val requestedAt: String?,
    @SerializedName("approved_at") val approvedAt: String?,
    @SerializedName("assigned_at") val assignedAt: String?,
    @SerializedName("expires_at") val expiresAt: String?,
    @SerializedName("deactivated_at") val deactivatedAt: String?,
    @SerializedName("created_at") val createdAt: String?,
    @SerializedName("updated_at") val updatedAt: String?
)

data class SubscriptionPackInfo(
    @SerializedName("name") val name: String,
    @SerializedName("sku") val sku: String,
    @SerializedName("price") val price: Double,
    @SerializedName("validity_months") val validityMonths: Int
)

data class CurrentSubscriptionResponse(
    @SerializedName("success") val success: Boolean,
    @SerializedName("subscription") val subscription: CurrentSubscription?
)

data class CurrentSubscription(
    @SerializedName("id") val id: Int,
    @SerializedName("pack") val pack: SubscriptionPackInfo?,
    @SerializedName("status") val status: String,
    @SerializedName("assigned_at") val assignedAt: String?,
    @SerializedName("expires_at") val expiresAt: String?,
    @SerializedName("is_valid") val isValid: Boolean
)

data class SubscriptionRequest(
    @SerializedName("sku") val sku: String? = null,
    @SerializedName("pack_sku") val packSku: String? = null
)

data class SubscriptionCreateResponse(
    @SerializedName("success") val success: Boolean,
    @SerializedName("message") val message: String?,
    @SerializedName("subscription") val subscription: SubscriptionInfo?
)

data class SubscriptionInfo(
    @SerializedName("id") val id: Int,
    @SerializedName("status") val status: String,
    @SerializedName("requested_at") val requestedAt: String?
)

data class SubscriptionHistoryResponse(
    @SerializedName("success") val success: Boolean,
    @SerializedName("history") val history: List<SubscriptionHistoryItem>?,
    @SerializedName("pagination") val pagination: Pagination?
)

data class SubscriptionHistoryItem(
    @SerializedName("id") val id: Int,
    @SerializedName("pack_name") val packName: String,
    @SerializedName("status") val status: String,
    @SerializedName("assigned_at") val assignedAt: String?,
    @SerializedName("expires_at") val expiresAt: String?
)

data class SubscriptionListResponse(
    @SerializedName("success") val success: Boolean,
    @SerializedName("subscriptions") val subscriptions: List<Subscription>?,
    @SerializedName("pagination") val pagination: Pagination?
)

data class Pagination(
    @SerializedName("page") val page: Int,
    @SerializedName("limit") val limit: Int,
    @SerializedName("total") val total: Int
)

data class DashboardResponse(
    @SerializedName("success") val success: Boolean,
    @SerializedName("data") val data: DashboardData?
)

data class DashboardData(
    @SerializedName("total_customers") val totalCustomers: Int,
    @SerializedName("active_subscriptions") val activeSubscriptions: Int,
    @SerializedName("pending_requests") val pendingRequests: Int,
    @SerializedName("total_revenue") val totalRevenue: Double,
    @SerializedName("recent_activities") val recentActivities: List<ActivityItem>?
)

data class ActivityItem(
    @SerializedName("type") val type: String,
    @SerializedName("customer") val customer: String?,
    @SerializedName("pack") val pack: String?,
    @SerializedName("timestamp") val timestamp: String
)

data class ApiResponse<T>(
    @SerializedName("success") val success: Boolean,
    @SerializedName("message") val message: String?,
    @SerializedName("data") val data: T?
)

data class ErrorResponse(
    @SerializedName("success") val success: Boolean,
    @SerializedName("message") val message: String
)
