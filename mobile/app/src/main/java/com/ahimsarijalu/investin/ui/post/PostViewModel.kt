package com.ahimsarijalu.investin.ui.post

import androidx.lifecycle.ViewModel
import com.ahimsarijalu.investin.data.repository.ExploreRepository
import com.ahimsarijalu.investin.utils.ApiCallback
import com.ahimsarijalu.investin.utils.token
import kotlinx.serialization.ExperimentalSerializationApi
import java.io.File

@ExperimentalSerializationApi
class PostViewModel(
    private val exploreRepository: ExploreRepository,
) : ViewModel() {
    fun addExplore(text: String) =
        exploreRepository.addExplore(token.value.toString(), text)

    fun uploadImage(
        token: String,
        file: File,
        docId: String,
        callback: ApiCallback
    ) = exploreRepository.uploadImage(token, file, docId, callback)
}