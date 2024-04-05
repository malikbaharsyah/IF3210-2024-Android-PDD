package com.example.bondoman_pdd.ui.settings

import SecureStorage
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.bondoman_pdd.AddTransactionActivity
import com.example.bondoman_pdd.R
import com.example.bondoman_pdd.databinding.FragmentSettingsBinding
import com.example.bondoman_pdd.ui.login.LoginActivity
import excelkt.workbook
import excelkt.write
import java.io.File


const val ACTION_RANDOMIZE_TRANSACTION = "android.intent.action.RANDOMIZE_TRANSACTION"

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
        return root
    }

    fun saveTransactionToExcel(filePath : String, nim : Int) {
        // Ambil nim dari email

        // Ambil data transaksi dari database
        val setupData = com.example.bondoman_pdd.data.transactions.setup.DatabaseHelper(requireContext())
        val listTransactions = setupData.getTransactions(nim)

        // Simpan data transaksi ke file excel

        workbook {
            sheet {
                row {
                    cell("Hello, World!")
                }
            }

            sheet {
                row {
                    cell("ID")
                    cell("Nama")
                    cell("Kategori")
                    cell("Jumlah")
                    cell("Tanggal")
                }

                for (transaction in listTransactions) {
                    row {
                        cell(transaction.id)
                        cell(transaction.judul)
                        cell(transaction.kategori)
                        cell(transaction.nominal)
                        cell(transaction.tanggal)
                    }
                }
            }

        }.write(File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS), filePath).toString())
        println(filePath)

    // Buka file excel yang sudah disimpan
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val logoutButton = view.findViewById<Button>(R.id.logoutbutton)
        val sendButton = view.findViewById<Button>(R.id.sendbutton)
        val randomizeTransactionButton = view.findViewById<Button>(R.id.randomizeTransactionbutton)
        val saveTransactionButton = view.findViewById<Button>(R.id.savebutton)
        // Set click listener on the logout button
        logoutButton.setOnClickListener {
            // delete token
            SecureStorage.deleteToken(requireContext())
            SecureStorage.deleteEmail(requireContext())
            Log.d("SettingsFragment","${SecureStorage.getToken(requireContext())} token removed")

            val intent = Intent(requireActivity(), LoginActivity::class.java)

            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK

            // Start LoginActivity
            startActivity(intent)

            requireActivity().finish()

            Toast.makeText(requireContext(), "Logout Success", Toast.LENGTH_LONG).show()
        }

        sendButton.setOnClickListener {
            val email = SecureStorage.getEmail(requireContext())
            val id = email?.substring(0, 8)
            val nim = id?.toInt()
            println("NIM : $nim")
            val filePath = nim.toString() + "transactions.xlsx"
            saveTransactionToExcel(filePath, nim!!)

            // Create an Intent to send an email
            val recipient = "13521008@std.stei.itb.ac.id"
            val subject = "Hello World"
            val message = "Semoga harimu indah bro"

            // Ni nanti ganti sama file xlsxnya kalo udah bisa
            val fileUri = Uri.parse(File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS), filePath).toString())

            sendEmail(recipient, subject, message, fileUri)
            
            randomizeTransactionButton.setOnClickListener {
                println("Randomize ditekan")
                randomizeTransaction()
                println("Randomize Transaction jalan")
                Toast.makeText(requireContext(), "Randomize Transaction", Toast.LENGTH_SHORT).show()
                val intent = Intent(requireContext(), AddTransactionActivity::class.java)
                startActivity(intent)
            }
        }

        saveTransactionButton.setOnClickListener {
            val email = SecureStorage.getEmail(requireContext())
            val id = email?.substring(0, 8)
            val nim = id?.toInt()
            println("NIM : $nim")

            val filePath = nim.toString() + "transactions.xlsx"
            saveTransactionToExcel(filePath, nim!!)

            Toast.makeText(requireContext(), "Save Transaction", Toast.LENGTH_SHORT).show()
            // Membuka file excel yang sudah disimpan

        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
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

    private fun randomizeTransaction() {
        val intent = Intent().also { intent ->
            intent.setAction(ACTION_RANDOMIZE_TRANSACTION)
        }
        requireContext().sendBroadcast(intent)
        println("Broadcast sent successfully")
    }
}