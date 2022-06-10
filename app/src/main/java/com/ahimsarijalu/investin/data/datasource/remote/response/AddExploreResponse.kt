package com.ahimsarijalu.investin.data.datasource.remote.response

import com.google.gson.annotations.SerializedName

data class AddExploreResponse(

	@field:SerializedName("data")
	val data: Data,

	@field:SerializedName("success")
	val success: Boolean,

	@field:SerializedName("message")
	val message: String
)

data class Data(

	@field:SerializedName("createdAt")
	val createdAt: String,

	@field:SerializedName("id")
	val id: String,

	@field:SerializedName("text")
	val text: String,

	@field:SerializedName("commentCounter")
	val commentCounter: Int
)
