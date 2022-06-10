package com.ahimsarijalu.investin.ui.profile

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.ahimsarijalu.investin.data.datasource.remote.response.UserDataItem
import com.ahimsarijalu.investin.data.repository.Result
import com.ahimsarijalu.investin.databinding.FragmentProfileBinding
import com.ahimsarijalu.investin.utils.ViewModelFactory
import com.ahimsarijalu.investin.utils.decimalToPercentage
import com.ahimsarijalu.investin.utils.showToast
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.serialization.ExperimentalSerializationApi

@ExperimentalSerializationApi
class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    private lateinit var profileViewModel: ProfileViewModel

    private lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupViewModel()
        showUser()
    }

    private fun showUser() {
        auth = Firebase.auth

        binding.root.visibility = View.GONE
        profileViewModel.getUserById(auth.currentUser?.uid.toString())
            .observe(requireActivity()) { result ->
                if (result != null) {
                    when (result) {
                        is Result.Error -> {
                            binding.progressBar.visibility = View.GONE
                            showToast(requireContext(), result.error)
                        }
                        is Result.Success -> {
                            binding.root.visibility = View.VISIBLE
                            binding.progressBar.visibility = View.GONE
                            setUserData(result.data.data[0])
                        }
                        is Result.Loading -> {
                            binding.progressBar.visibility = View.VISIBLE
                        }
                    }
                }
            }

    }

    private fun setUserData(user: UserDataItem) {
        binding.let {
            Glide.with(this)
                .load(user.avatar)
                .circleCrop()
                .into(it.userAvatar)
        }
        binding.apply {
            val location = "${user.city}, ${user.province}"

            nameTv.text = user.displayName
            categoryTv.text = user.category
            locationTv.text = location
            descTv.text = user.bio
            if (!user.investorRole) {
                rgTitleTv.visibility = View.VISIBLE
                revenueGrowthTv.visibility = View.VISIBLE
                revenueGrowthTv.text = user.revenueGrowth?.let { decimalToPercentage(it) }
            }
        }

        setupButtonAction(user)
    }

    private fun setupButtonAction(user: UserDataItem) {
        binding.apply {
            emailButton.setOnClickListener {
                val intent = Intent(Intent.ACTION_SENDTO)
                intent.setDataAndType(Uri.parse("mailto:${user.email}"), "plain/text")
                startActivity(Intent.createChooser(intent, "Select your Email app"))
            }
        }
    }

    private fun setupViewModel() {
        profileViewModel = ViewModelProvider(
            requireActivity(),
            ViewModelFactory(requireContext())
        )[ProfileViewModel::class.java]
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}