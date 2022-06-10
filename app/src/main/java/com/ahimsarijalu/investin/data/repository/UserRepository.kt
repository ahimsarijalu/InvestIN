package com.ahimsarijalu.investin.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.ahimsarijalu.investin.data.datasource.remote.response.AvatarUploadResponse
import com.ahimsarijalu.investin.data.datasource.remote.response.UserResponse
import com.ahimsarijalu.investin.data.datasource.remote.retrofit.ApiService
import com.ahimsarijalu.investin.utils.ApiCallback
import com.ahimsarijalu.investin.utils.prepareImage
import com.ahimsarijalu.investin.utils.wrapEspressoIdlingResource
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File

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

    fun uploadAvatar(
        token: String,
        file: File,
        userId: String,
        callback: ApiCallback
    ) {
        val client = apiService.uploadAvatar(
            " Bearer $token",
            prepareImage(file),
            userId
        )

        client.enqueue(object : Callback<AvatarUploadResponse> {
            override fun onResponse(
                call: Call<AvatarUploadResponse>,
                response: Response<AvatarUploadResponse>
            ) {
                if (response.isSuccessful) {
                    callback.onResponse(true)
                } else {
                    callback.onResponse(false)
                }
            }

            override fun onFailure(call: Call<AvatarUploadResponse>, t: Throwable) {
                callback.onResponse(false)
            }

        })
    }
}