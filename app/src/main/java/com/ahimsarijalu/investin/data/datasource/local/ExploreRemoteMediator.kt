package com.ahimsarijalu.investin.data.datasource.local

import android.util.Log
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.ahimsarijalu.investin.data.datasource.local.database.ExploreDatabase
import com.ahimsarijalu.investin.data.datasource.local.database.RemoteKeys
import com.ahimsarijalu.investin.data.datasource.remote.response.DataItem
import com.ahimsarijalu.investin.data.datasource.remote.retrofit.ApiService
import kotlinx.serialization.ExperimentalSerializationApi

@ExperimentalSerializationApi
@OptIn(ExperimentalPagingApi::class)
class ExploreRemoteMediator(
    private val token: String,
    private val database: ExploreDatabase,
    private val apiService: ApiService
) : RemoteMediator<Int, DataItem>() {
    override suspend fun initialize(): InitializeAction {
        return InitializeAction.LAUNCH_INITIAL_REFRESH
    }

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, DataItem>
    ): MediatorResult {
        val page = when (loadType) {
            LoadType.REFRESH -> {
                Log.d("LoadType", "Refresh terpanggil")
                val remoteKeys = getRemoteKeyClosestToCurrentPosition(state)
                remoteKeys?.nextKey?.minus(1) ?: INITIAL_PAGE_INDEX
            }
            LoadType.PREPEND -> {
                Log.d("LoadType", "Prepend terpanggil")
                val remoteKeys = getRemoteKeyForFirstItem(state)
                val prevKey = remoteKeys?.prevKey
                    ?: return MediatorResult.Success(endOfPaginationReached = remoteKeys != null)
                prevKey
            }
            LoadType.APPEND -> {
                Log.d("LoadType", "Append terpanggil")
                val remoteKeys = getRemoteKeyForLastItem(state)
                val nextKey = remoteKeys?.nextKey
                    ?: return MediatorResult.Success(endOfPaginationReached = remoteKeys != null)
                nextKey
            }
        }

        try {
            val response = apiService.getAllExplore("Bearer $token", page, state.config.pageSize)

            val responseData = response.data

            val endOfPaginationReached = responseData.isEmpty()

            database.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    database.remoteKeysDao().deleteRemoteKeys()
                    database.exploreDao().deleteAllExploreFromLocal()
                }
                val prevKeys = if (page == 1) null else page - 1
                val nextKeys = if (endOfPaginationReached) null else page + 1

                val keys = responseData.map {
                    RemoteKeys(id = it.id, prevKey = prevKeys, nextKey = nextKeys)
                }
                database.remoteKeysDao().insertAll(keys)

                database.exploreDao().insertExplore(responseData)
            }
            return MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)
        } catch (exception: Exception) {
            Log.d("DEBUG", exception.message.toString())
            return MediatorResult.Error(exception)
        }
    }

    private suspend fun getRemoteKeyForLastItem(state: PagingState<Int, DataItem>): RemoteKeys? {
        return state.pages.lastOrNull { it.data.isNotEmpty() }?.data?.lastOrNull()?.let { data ->
            database.remoteKeysDao().getRemoteKeysId(data.id)
        }
    }

    private suspend fun getRemoteKeyForFirstItem(state: PagingState<Int, DataItem>): RemoteKeys? {
        return state.pages.firstOrNull { it.data.isNotEmpty() }?.data?.firstOrNull()?.let { data ->
            database.remoteKeysDao().getRemoteKeysId(data.id)
        }
    }

    private suspend fun getRemoteKeyClosestToCurrentPosition(state: PagingState<Int, DataItem>): RemoteKeys? {
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.id?.let { id ->
                database.remoteKeysDao().getRemoteKeysId(id)
            }
        }
    }

    private companion object {
        const val INITIAL_PAGE_INDEX = 1
    }

}