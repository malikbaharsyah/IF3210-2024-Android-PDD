package com.example.bondoman_pdd.ui.addTransactions

import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.bondoman_pdd.MainActivity
import com.example.bondoman_pdd.R
import com.example.bondoman_pdd.databinding.ActivityAddTransactionBinding
import com.example.bondoman_pdd.ui.settings.ACTION_RANDOMIZE_TRANSACTION

class AddTransactionFragment : Fragment() {

    private lateinit var binding: ActivityAddTransactionBinding
    private lateinit var broadcastReceiver: MyBroadcastReceiver
    private var spinner: Spinner? = null

    companion object {
        fun newInstance() = AddTransactionFragment()
    }

    private val viewModel: AddTransactionsViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // TODO: Use the ViewModel
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.activity_add_transaction, container, false)
        val spinner: Spinner = view.findViewById(R.id.kategori)
        val saveButton: Button = view.findViewById(R.id.save_add_transaction)

        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter.createFromResource(
            requireContext(),
            R.array.kategori_array,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            // Specify the layout to use when the list of choices appears
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            // Apply the adapter to the spinner
            spinner.adapter = adapter
        }

        // Set an item selected listener for the spinner
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                // Get the selected item from the spinner
                val selectedItem = spinner.getItemAtPosition(position).toString()

                // Show toast based on the selected item
                when (selectedItem) {
                    "Penjualan" -> {
                        Toast.makeText(requireContext(), "Penjualan dipilih", Toast.LENGTH_SHORT).show()
                    }
                    "Pembelian" -> {
                        Toast.makeText(requireContext(), "Pembelian dipilih", Toast.LENGTH_SHORT).show()
                    }
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                // Do nothing when nothing is selected
            }
        }

        // Set the first item as the prompt
        spinner.prompt = resources.getStringArray(R.array.kategori_array)[0]
        spinner.setSelection(0)

        // Set click listener for save button
        saveButton.setOnClickListener {
            // Get the selected item from the spinner
            val selectedItem = spinner.selectedItem.toString()

            // Perform action based on the selected item
            when (selectedItem) {
                "Penjualan" -> {
                    // Handle save action for Penjualan
                    // Call a function or method to save Penjualan
                }
                "Pembelian" -> {
                    // Handle save action for Pembelian
                    // Call a function or method to save Pembelian
                }
            }
        }

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = ActivityAddTransactionBinding.bind(view)


//         Initialize the BroadcastReceiver
        broadcastReceiver = MyBroadcastReceiver(binding)

        // Register the BroadcastReceiver
        val filter = IntentFilter().apply {
            addAction(ACTION_RANDOMIZE_TRANSACTION)
            addAction(Intent.ACTION_AIRPLANE_MODE_CHANGED)
        }
        val listenToBroadcastsFromOtherApps = true
        val receiverFlags = if (listenToBroadcastsFromOtherApps) {
            ContextCompat.RECEIVER_EXPORTED
        } else {
            ContextCompat.RECEIVER_NOT_EXPORTED
        }
        requireContext().registerReceiver(broadcastReceiver, filter, receiverFlags)

        // Find the logout button by its ID
        val saveTransactionButton = view.findViewById<Button>(R.id.save_add_transaction)

        // Set click listener on the logout button
        saveTransactionButton.setOnClickListener {
            // Create an Intent to navigate back to LoginActivity
            val intent = Intent(requireActivity(), MainActivity::class.java)

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

        // Unregister the BroadcastReceiver
        requireContext().unregisterReceiver(broadcastReceiver)
    }
}
