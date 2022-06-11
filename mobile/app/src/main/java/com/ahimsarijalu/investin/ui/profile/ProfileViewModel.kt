package com.ahimsarijalu.investin.ui.profile

import androidx.lifecycle.ViewModel
import com.ahimsarijalu.investin.data.repository.UserRepository
import com.ahimsarijalu.investin.utils.token
import com.google.firebase.ktx.Firebase

class ProfileViewModel(
    private val userRepository: UserRepository
) : ViewModel() {

    fun getUserById(
        userId: String
    ) = userRepository.getUserById(token.value.toString(), userId)
}