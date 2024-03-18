package com.example.bondoman_pdd.ui.settings

import SecureStorage
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.bondoman_pdd.R
import com.example.bondoman_pdd.databinding.FragmentSettingsBinding
import com.example.bondoman_pdd.ui.login.LoginActivity

class SettingsFragment : Fragment() {

    private var _binding: FragmentSettingsBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val transactionsViewModel =
            ViewModelProvider(this).get(SettingsViewModel::class.java)

        _binding = FragmentSettingsBinding.inflate(inflater, container, false)
        val root: View = binding.root

//        val textView: TextView = binding.textSettings
//        transactionsViewModel.text.observe(viewLifecycleOwner) {
//            textView.text = it
//        }
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Find the logout button by its ID
        val logoutButton = view.findViewById<Button>(R.id.logoutbutton)

        // Set click listener on the logout button
        logoutButton.setOnClickListener {
            // delete token
            SecureStorage.deleteToken(requireContext())
            Log.d("SettingsFragment","${SecureStorage.getToken(requireContext())} token removed")

            // Create an Intent to navigate back to LoginActivity
            val intent = Intent(requireActivity(), LoginActivity::class.java)

            // Add flags to clear the activity stack so that LoginActivity becomes the top activity
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK

            // Start LoginActivity
            startActivity(intent)

            // Optionally, finish the current activity (fragment activity) if you want to clear it from the stack
            requireActivity().finish()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}