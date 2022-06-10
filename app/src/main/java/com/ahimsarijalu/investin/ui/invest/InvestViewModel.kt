package com.ahimsarijalu.investin.ui.invest

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ahimsarijalu.investin.data.repository.UserRepository
import com.ahimsarijalu.investin.utils.token
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class InvestViewModel(
    private val userRepository: UserRepository
) : ViewModel() {

    fun getUserById(
        userId: String
    ) = userRepository.getUserById(token.value.toString(), userId)
}