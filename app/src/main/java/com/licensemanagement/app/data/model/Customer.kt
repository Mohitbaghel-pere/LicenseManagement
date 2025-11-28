package com.licensemanagement.app.data.model

import com.google.gson.annotations.SerializedName

data class Customer(
    @SerializedName("id") val id: Int,
    @SerializedName("user_id") val userId: Int,
    @SerializedName("name") val name: String,
    @SerializedName("email") val email: String,
    @SerializedName("phone") val phone: String,
    @SerializedName("created_at") val createdAt: String?,
    @SerializedName("updated_at") val updatedAt: String?
)

data class CustomerListResponse(
    @SerializedName("success") val success: Boolean,
    @SerializedName("customers") val customers: List<Customer>?,
    @SerializedName("pagination") val pagination: Pagination?
)
