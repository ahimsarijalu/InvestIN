package com.ahimsarijalu.investin.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.ahimsarijalu.investin.data.datasource.remote.response.UserResponse
import com.ahimsarijalu.investin.data.datasource.remote.retrofit.ApiService
import com.ahimsarijalu.investin.utils.wrapEspressoIdlingResource

class UserRepository(
    private val apiService: ApiService
) {
    fun getUserById(token: String, userId: String): LiveData<Result<UserResponse>> = liveData {
        emit(Result.Loading)
        wrapEspressoIdlingResource {
            try {
                val response = apiService.getUserById("Bearer $token", userId)
                if (response.success) {
                    emit(Result.Success(response))
                } else {
                    emit(Result.Error(response.message))
                }
            } catch (e: Exception) {
                emit(Result.Error(e.message.toString()))
            }
        }

    }
}