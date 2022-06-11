package com.ahimsarijalu.investin.ui.settings.edit_profile

import androidx.lifecycle.ViewModel
import com.ahimsarijalu.investin.data.repository.UserRepository
import com.ahimsarijalu.investin.utils.ApiCallback
import java.io.File

class EditProfileViewModel(
    private val userRepository: UserRepository
): ViewModel() {
    fun uploadAvatar(
        token: String,
        file: File,
        userId: String,
        callback: ApiCallback
    ) = userRepository.uploadAvatar(token, file, userId, callback)
}