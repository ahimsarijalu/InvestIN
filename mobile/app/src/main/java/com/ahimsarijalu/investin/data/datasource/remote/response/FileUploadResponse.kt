package com.ahimsarijalu.investin.data.datasource.remote.response

import com.google.gson.annotations.SerializedName

data class FileUploadResponse(

	@field:SerializedName("data")
	val data: UploadData,

	@field:SerializedName("success")
	val success: Boolean,

	@field:SerializedName("message")
	val message: String
)

data class UploadData(

	@field:SerializedName("imageUrl")
	val imageUrl: List<String>
)
