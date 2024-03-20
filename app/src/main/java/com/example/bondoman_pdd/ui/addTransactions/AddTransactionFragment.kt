package com.example.bondoman_pdd.ui.addTransactions

import android.content.Context
import android.content.IntentFilter
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.bondoman_pdd.databinding.FragmentAddTransactionBinding
import com.example.bondoman_pdd.ui.settings.ACTION_RANDOMIZE_TRANSACTION

class AddTransactionFragment : Fragment() {

    private lateinit var binding: FragmentAddTransactionBinding
    private lateinit var broadcastReceiver: MyBroadcastReceiver

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
        val filter = IntentFilter(ACTION_RANDOMIZE_TRANSACTION)
        val listenToBroadcastsFromOtherApps = false
        val receiverFlags = if (listenToBroadcastsFromOtherApps) {
            ContextCompat.RECEIVER_EXPORTED
        } else {
            ContextCompat.RECEIVER_NOT_EXPORTED
        }
        requireContext().registerReceiver(broadcastReceiver, filter, receiverFlags)
    }

    override fun onDestroyView() {
        super.onDestroyView()

        // Unregister the BroadcastReceiver
        requireContext().unregisterReceiver(broadcastReceiver)
    }
}
