package com.ahimsarijalu.investin.data.datasource.remote.retrofit

import com.ahimsarijalu.investin.data.datasource.remote.response.ExploreResponse
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface ApiService {
    @GET("explore")
    suspend fun getAllExplore(
        @Header("Authorization") token: String,
        @Query("page") page: Int = 1,
        @Query("size") size: Int = 1
    ): ExploreResponse
}