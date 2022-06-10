package com.ahimsarijalu.investin.utils

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import com.ahimsarijalu.investin.di.Injection
import com.ahimsarijalu.investin.ui.home.HomeViewModel
import com.ahimsarijalu.investin.ui.invest.InvestViewModel
import com.ahimsarijalu.investin.ui.post.PostViewModel
import com.ahimsarijalu.investin.ui.profile.ProfileViewModel
import kotlinx.serialization.ExperimentalSerializationApi

@ExperimentalSerializationApi
class ViewModelFactory(private val context: Context) :
    ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(HomeViewModel::class.java) -> {
                HomeViewModel(Injection.provideExploreRepository(context)) as T
            }
            modelClass.isAssignableFrom(ProfileViewModel::class.java) -> {
                ProfileViewModel(Injection.provideUserRepository()) as T
            }
            modelClass.isAssignableFrom(InvestViewModel::class.java) -> {
                InvestViewModel(Injection.provideUserRepository()) as T
            }
            modelClass.isAssignableFrom(PostViewModel::class.java) -> {
                PostViewModel(Injection.provideExploreRepository(context)) as T
            }
            else -> throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
        }
    }
}