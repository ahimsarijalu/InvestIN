package com.ahimsarijalu.investin.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.ahimsarijalu.investin.data.datasource.remote.response.DataItem
import com.ahimsarijalu.investin.data.repository.ExploreRepository
import com.ahimsarijalu.investin.utils.token
import kotlinx.serialization.ExperimentalSerializationApi


@ExperimentalSerializationApi
class HomeViewModel(
    exploreRepository: ExploreRepository
) : ViewModel() {

    val explore: LiveData<PagingData<DataItem>> = Transformations.switchMap(token) {
        exploreRepository.getExplore(it).cachedIn(viewModelScope)
    }

}