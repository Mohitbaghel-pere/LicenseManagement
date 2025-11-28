package com.licensemanagement.app.data.model

import com.google.gson.annotations.SerializedName

data class SubscriptionPack(
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String,
    @SerializedName("description") val description: String,
    @SerializedName("sku") val sku: String,
    @SerializedName("price") val price: Double,
    @SerializedName("validity_months") val validityMonths: Int,
    @SerializedName("created_at") val createdAt: String?,
    @SerializedName("updated_at") val updatedAt: String?
)

data class SubscriptionPackListResponse(
    @SerializedName("success") val success: Boolean,
    @SerializedName("packs") val packs: List<SubscriptionPack>?,
    @SerializedName("pagination") val pagination: Pagination?
)

data class SubscriptionPackCreateRequest(
    @SerializedName("name") val name: String,
    @SerializedName("description") val description: String,
    @SerializedName("sku") val sku: String,
    @SerializedName("price") val price: Double,
    @SerializedName("validity_months") val validityMonths: Int
)
