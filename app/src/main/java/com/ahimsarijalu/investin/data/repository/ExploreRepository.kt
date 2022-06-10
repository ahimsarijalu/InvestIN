package com.ahimsarijalu.investin.data.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import androidx.paging.*
import com.ahimsarijalu.investin.data.datasource.local.ExploreRemoteMediator
import com.ahimsarijalu.investin.data.datasource.local.database.ExploreDatabase
import com.ahimsarijalu.investin.data.datasource.remote.response.AddExploreResponse
import com.ahimsarijalu.investin.data.datasource.remote.response.DataItem
import com.ahimsarijalu.investin.data.datasource.remote.response.FileUploadResponse
import com.ahimsarijalu.investin.data.datasource.remote.response.UserDataItem
import com.ahimsarijalu.investin.data.datasource.remote.retrofit.ApiService
import com.ahimsarijalu.investin.utils.ApiCallback
import com.ahimsarijalu.investin.utils.reduceFileImage
import com.ahimsarijalu.investin.utils.wrapEspressoIdlingResource
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import kotlinx.serialization.ExperimentalSerializationApi
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File


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

    fun addExplore(
        token: String,
        text: String
    ): LiveData<Result<AddExploreResponse>> = liveData {
        emit(Result.Loading)
        wrapEspressoIdlingResource {
            try {
                val response = apiService.addExplore("Bearer $token", text)

                val currentUser = Firebase.auth.currentUser
                val db = Firebase.firestore

                if (response.success) {
                    if (currentUser != null) {
                        db.collection("users")
                            .document(currentUser.uid)
                            .get()
                            .addOnSuccessListener {
                                val userData = it.toObject<UserDataItem>()!!

                                Log.d("DEBUG", userData.displayName)

                                val data = mapOf(
                                    "authorId" to userData.userId,
                                    "author" to userData.displayName,
                                    "avatar" to userData.avatar,
                                    "category" to userData.category
                                )

                                db.collection("explore")
                                    .document(response.data.id)
                                    .update(data)
                            }

                        emit(Result.Success(response))

                    }
                } else {
                    emit(Result.Error(response.message))
                }
            } catch (e: Exception) {
                emit(Result.Error(e.message.toString()))
            }
        }
    }


    fun uploadImage(
        token: String,
        file: File,
        docId: String,
        callback: ApiCallback
    ) {
        val client = apiService.addImageToExploreById(
            " Bearer $token",
            prepareImage(file),
            docId
        )

        client.enqueue(object : Callback<FileUploadResponse> {
            override fun onResponse(
                call: Call<FileUploadResponse>,
                response: Response<FileUploadResponse>
            ) {
                if (response.isSuccessful) {
                    callback.onResponse(true)
                } else {
                    callback.onResponse(false)
                    Log.d("UploadDebug", "Error: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<FileUploadResponse>, t: Throwable) {
                callback.onResponse(false)
                Log.d("UploadDebug", "Error: ${t.message.toString()}")
            }

        })
    }


    private fun prepareImage(getFile: File): MultipartBody.Part {
        val file = reduceFileImage(getFile)
        val requestImageFile = file.asRequestBody("image/jpeg".toMediaType())

        return MultipartBody.Part.createFormData(
            "photo",
            file.name,
            requestImageFile
        )
    }
}