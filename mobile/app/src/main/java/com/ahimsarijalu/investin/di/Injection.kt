package com.ahimsarijalu.investin.di

import android.content.Context
import com.ahimsarijalu.investin.data.datasource.local.database.ExploreDatabase
import com.ahimsarijalu.investin.data.datasource.remote.retrofit.ApiConfig
import com.ahimsarijalu.investin.data.repository.ExploreRepository
import com.ahimsarijalu.investin.data.repository.UserRepository
import kotlinx.serialization.ExperimentalSerializationApi

@ExperimentalSerializationApi
object Injection {
    fun provideExploreRepository(context: Context): ExploreRepository {
        val database = ExploreDatabase.getDatabase(context)
        val apiService = ApiConfig.getApiService()
        return ExploreRepository(database, apiService)
    }

    fun provideUserRepository(): UserRepository {
        val apiService = ApiConfig.getApiService()
        return UserRepository(apiService)
    }
}