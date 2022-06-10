package com.ahimsarijalu.investin.data.datasource.local.database

import androidx.lifecycle.LiveData
import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.ahimsarijalu.investin.data.datasource.remote.response.DataItem

@Dao
interface ExploreDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertExplore(explore: List<DataItem>)

    @Query("SELECT * FROM explore")
    fun getAllExploreFromLocal(): PagingSource<Int, DataItem>

    @Query("DELETE FROM explore")
    suspend fun deleteAllExploreFromLocal()
}