package com.ahimsarijalu.investin.ui.settings

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat.finishAffinity
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import com.ahimsarijalu.investin.R
import com.ahimsarijalu.investin.ui.MainActivity
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class SettingsFragment : PreferenceFragmentCompat() {

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.preferences, rootKey)

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

}