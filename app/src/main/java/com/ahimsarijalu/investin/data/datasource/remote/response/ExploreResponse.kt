package com.ahimsarijalu.investin.data.datasource.remote.response

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class ExploreResponse(

	@field:SerializedName("data")
	val data: List<DataItem>,

	@field:SerializedName("success")
	val success: Boolean,

	@field:SerializedName("message")
	val message: String
) : Parcelable

@Entity(tableName = "explore")
@Parcelize
data class DataItem(

	@field:SerializedName("createdAt")
	val createdAt: String,

	@field:SerializedName("likeUsers")
	val likeUsers: List<String>,

	@field:SerializedName("author")
	val author: String,

	@field:SerializedName("imageUrl")
	val imageUrl: List<String>?,

	@field:SerializedName("text")
	val text: String,

	@field:SerializedName("avatar")
	val avatar: String,

	@PrimaryKey
	@field:SerializedName("id")
	val id: String,

	@field:SerializedName("category")
	val category: String
) : Parcelable
