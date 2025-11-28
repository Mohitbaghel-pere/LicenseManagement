package com.licensemanagement.app.data.model

import com.google.gson.annotations.SerializedName

data class DashboardResponse(
    @SerializedName("success") val success: Boolean,
    @SerializedName("data") val data: DashboardData
)

data class DashboardData(
    @SerializedName("total_customers") val totalCustomers: Int,
    @SerializedName("active_subscriptions") val activeSubscriptions: Int,
    @SerializedName("pending_requests") val pendingRequests: Int,
    @SerializedName("total_revenue") val totalRevenue: Double,
    @SerializedName("recent_activities") val recentActivities: List<Activity>
)

data class Activity(
    @SerializedName("type") val type: String,
    @SerializedName("customer") val customer: String?,
    @SerializedName("pack") val pack: String,
    @SerializedName("timestamp") val timestamp: String
)

