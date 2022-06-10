package com.ahimsarijalu.investin.data.datasource.remote.retrofit

import com.ahimsarijalu.investin.data.datasource.remote.response.AddExploreResponse
import com.ahimsarijalu.investin.data.datasource.remote.response.ExploreResponse
import com.ahimsarijalu.investin.data.datasource.remote.response.FileUploadResponse
import com.ahimsarijalu.investin.data.datasource.remote.response.UserResponse
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.http.*

interface ApiService {
    @GET("explore")
    suspend fun getAllExplore(
        @Header("Authorization") token: String,
        @Query("page") page: Int = 1,
        @Query("size") size: Int = 1
    ): ExploreResponse

    @FormUrlEncoded
    @POST("explore")
    suspend fun addExplore(
        @Header("Authorization") token: String,
        @Field("text") text: String
    ): AddExploreResponse

    @GET("user/{userId}")
    suspend fun getUserById(
        @Header("Authorization") token: String,
        @Path("userId") userId: String
    ): UserResponse

    @Multipart
    @POST("explore/upload/{docId}")
    fun addImageToExploreById(
        @Header("Authorization") token: String,
        @Part file: MultipartBody.Part,
        @Path("docId") docId: String
    ): Call<FileUploadResponse>
}