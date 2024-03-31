package com.example.bondoman_pdd.ui.addTransactions
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.bondoman_pdd.R
import com.example.bondoman_pdd.databinding.FragmentAddTransactionBinding
import com.example.bondoman_pdd.ui.settings.ACTION_RANDOMIZE_TRANSACTION


class AddTransactionFragment : Fragment() {

    private lateinit var binding: FragmentAddTransactionBinding
    private lateinit var broadcastReceiver: MyBroadcastReceiver
    private var spinner: Spinner? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAddTransactionBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Initialize the BroadcastReceiver
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
    }

    override fun onResume() {
        super.onResume()

        if (spinner == null) {
            initSpinner()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()

        // Unregister the BroadcastReceiver
        requireContext().unregisterReceiver(broadcastReceiver)
    }

    private fun initSpinner() {
        // Access the items of the list
        val languages = resources.getStringArray(R.array.kategori_array)

        // Access the spinner
        spinner = binding.kategori
        spinner?.let {
            val adapter = ArrayAdapter(requireContext(),
                android.R.layout.simple_spinner_item, languages)
            it.adapter = adapter

            it.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>,
                    view: View,
                    position: Int,
                    id: Long
                ) {
                    Toast.makeText(
                        requireContext(),"Selected Item: "+ languages[position],
                        Toast.LENGTH_SHORT
                    ).show()
                }

                override fun onNothingSelected(parent: AdapterView<*>) {
                    // Handle no selection
                }
            }
        }
    }
}
