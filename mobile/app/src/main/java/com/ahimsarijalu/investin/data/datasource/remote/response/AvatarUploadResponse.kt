package com.ahimsarijalu.investin.data.datasource.remote.response

import com.google.gson.annotations.SerializedName

data class AvatarUploadResponse(

	@field:SerializedName("data")
	val data: AvatarData,

	@field:SerializedName("success")
	val success: Boolean,

	@field:SerializedName("message")
	val message: String
)

data class AvatarData(

	@field:SerializedName("avatar")
	val avatar: String
)
