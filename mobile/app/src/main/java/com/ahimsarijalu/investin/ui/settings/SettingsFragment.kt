package com.ahimsarijalu.investin.ui.settings

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat.finishAffinity
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import com.ahimsarijalu.investin.R
import com.ahimsarijalu.investin.data.datasource.remote.response.UserDataItem
import com.ahimsarijalu.investin.ui.MainActivity
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase

class SettingsFragment : PreferenceFragmentCompat() {

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.preferences, rootKey)
        setPreferences()

        val logoutPreference = findPreference<Preference>("logout")
        logoutPreference?.setOnPreferenceClickListener {
            AlertDialog.Builder(requireContext()).apply {
                setTitle(getString(R.string.logout_question))
                setNegativeButton(getString(R.string.cancel_text), null)
                setPositiveButton(getString(R.string.logout)) { _, _ ->
                    Firebase.auth.signOut()
                    Intent(requireContext(), MainActivity::class.java).apply {
                        startActivity(this)
                        finishAffinity(requireActivity())
                    }
                }
                create()
                show()
            }

            true
        }
    }

    private fun setPreferences() {
        val currentUser = Firebase.auth.currentUser
        val db = Firebase.firestore

        if (currentUser != null) {
            db.collection("users").document(currentUser.uid).get().addOnSuccessListener { result ->
                val userData = result.toObject<UserDataItem>()

                if (userData != null) {
                    if (userData.investorRole) {
                        val financePreference = findPreference<Preference>("edit_financial_data")
                        if (financePreference != null) {
                            preferenceScreen.removePreference(financePreference)
                        }
                    }
                }
            }
        }

    }

}