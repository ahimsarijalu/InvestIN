package com.ahimsarijalu.investin.data.datasource.remote.response

import com.google.gson.annotations.SerializedName

data class UserResponse(

    @field:SerializedName("data")
    val data: List<UserDataItem>,

    @field:SerializedName("success")
    val success: Boolean,

    @field:SerializedName("message")
    val message: String
)

data class UserDataItem(

    @field:SerializedName("createdAt")
    val createdAt: String = "",

    @field:SerializedName("investorRole")
    val investorRole: Boolean = false,

    @field:SerializedName("province")
    val province: String = "",

    @field:SerializedName("city")
    val city: String = "",

    @field:SerializedName("displayName")
    val displayName: String = "",

    @field:SerializedName("organization")
    val organization: String = "",

    @field:SerializedName("bio")
    val bio: String = "",

    @field:SerializedName("avatar")
    val avatar: String = "",

    @field:SerializedName("category")
    val category: String = "",

    @field:SerializedName("userId")
    val userId: String = "",

    @field:SerializedName("email")
    val email: String = "",

    @field:SerializedName("revenueGrowth")
    val revenueGrowth: Float? = 0.0f
)
