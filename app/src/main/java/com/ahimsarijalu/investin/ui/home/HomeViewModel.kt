package com.ahimsarijalu.investin.ui.home

import androidx.lifecycle.*
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.ahimsarijalu.investin.data.datasource.remote.response.DataItem
import com.ahimsarijalu.investin.data.repository.ExploreRepository
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.serialization.ExperimentalSerializationApi
import com.ahimsarijalu.investin.data.repository.Result.Success


@ExperimentalSerializationApi
class HomeViewModel(
    exploreRepository: ExploreRepository
) : ViewModel() {

    private val _token = MutableLiveData<String>()
    val token: LiveData<String> = _token


    val explore: LiveData<PagingData<DataItem>> = Transformations.switchMap(token) {
        exploreRepository.getExplore(it).cachedIn(viewModelScope)
    }

    fun getToken()  {
        val auth = Firebase.auth
        auth.currentUser!!.getIdToken(true)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val idToken = task.result.token.toString()
                    _token.value = idToken
                }
            }
    }

}