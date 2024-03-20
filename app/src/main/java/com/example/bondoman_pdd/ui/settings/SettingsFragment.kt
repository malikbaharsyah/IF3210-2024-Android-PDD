package com.example.bondoman_pdd.ui.settings

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
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

        val logoutButton = view.findViewById<Button>(R.id.logoutbutton)
        val sendButton = view.findViewById<Button>(R.id.sendbutton)
        val randomizeTransactionButton = view.findViewById<Button>(R.id.randomizeTransactionbutton)

        // Set click listener on the logout button
        logoutButton.setOnClickListener {
            // Call the logout function
            logout()
        }

        sendButton.setOnClickListener {
            // Create an Intent to send an email
            val recipient = "13521008@std.stei.itb.ac.id"
            val subject = "Hello World"
            val message = "Semoga harimu indah bro"

            // Ni nanti ganti sama file xlsxnya kalo udah bisa
            val fileUri = Uri.parse("content://path/to/your/file.xlsx")

            sendEmail(recipient, subject, message, fileUri)
        }

        randomizeTransactionButton.setOnClickListener {
            // Create an Intent to open the AddTransactionFragment
            Toast.makeText(requireContext(), "Randomize Transaction", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun logout() {
        val intent = Intent(requireActivity(), LoginActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
        requireActivity().finish()
    }

    private fun sendEmail(recipient: String, subject: String, message: String, fileUri: Uri) {
        val intent = Intent(Intent.ACTION_SEND)
        intent.type = "application/octet-stream"
        intent.putExtra(Intent.EXTRA_EMAIL, arrayOf(recipient))
        intent.putExtra(Intent.EXTRA_SUBJECT, subject)
        intent.putExtra(Intent.EXTRA_TEXT, message)

        // Set package name to Gmail
        intent.setPackage("com.google.android.gm")

        // Add the file as an attachment
        intent.putExtra(Intent.EXTRA_STREAM, fileUri)

        try {
            startActivity(intent)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}