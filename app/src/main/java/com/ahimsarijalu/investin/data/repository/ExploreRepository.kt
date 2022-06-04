package com.ahimsarijalu.investin.data.repository

import androidx.lifecycle.LiveData
import androidx.paging.*
import com.ahimsarijalu.investin.data.datasource.local.ExploreRemoteMediator
import com.ahimsarijalu.investin.data.datasource.local.database.ExploreDatabase
import com.ahimsarijalu.investin.data.datasource.remote.response.DataItem
import com.ahimsarijalu.investin.data.datasource.remote.retrofit.ApiService
import kotlinx.serialization.ExperimentalSerializationApi

@ExperimentalSerializationApi
class ExploreRepository(
    private val exploreDatabase: ExploreDatabase,
    private val apiService: ApiService
) {
    fun getExplore(token: String): LiveData<PagingData<DataItem>> {
        @OptIn(ExperimentalPagingApi::class)
        return Pager(
            config = PagingConfig(
                pageSize = 5
            ),
            remoteMediator = ExploreRemoteMediator(token, exploreDatabase, apiService),
            pagingSourceFactory = {
                exploreDatabase.exploreDao().getAllExploreFromLocal()
            }
        ).liveData
    }
}