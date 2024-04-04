package com.example.bondoman_pdd.ui.transactions

import TransactionAdapter
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.bondoman_pdd.AddTransactionActivity
import com.example.bondoman_pdd.R
import com.example.bondoman_pdd.data.model.Transactions
import com.example.bondoman_pdd.databinding.FragmentTransactionsBinding
import com.example.bondoman_pdd.ui.addTransactions.AddTransactionFragment
import com.example.bondoman_pdd.ui.chart.ChartFragment
import com.google.android.material.floatingactionbutton.FloatingActionButton

class TransactionsFragment : Fragment() {
    private var _binding: FragmentTransactionsBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private lateinit var rvTransaction : RecyclerView
    private var listTransactions : ArrayList<Transactions> = arrayListOf()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val transactionsViewModel =
            ViewModelProvider(this).get(TransactionsViewModel::class.java)

        _binding = FragmentTransactionsBinding.inflate(inflater, container, false)
        val root: View = binding.root

//        val textView: TextView = binding.tex
//        transactionsViewModel.text.observe(viewLifecycleOwner) {
//            textView.text = it
//        }

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        rvTransaction.layoutManager = LinearLayoutManager(context)
        val setupData = com.example.bondoman_pdd.data.transactions.setup.DatabaseHelper(requireContext())
        val listTransactions = setupData.getTransactions(13521028)

        println("R.id.recyclerview : ${R.id.recyclerview}")
        val rvTransaction : RecyclerView = view.findViewById(R.id.recyclerview)
        rvTransaction.layoutManager = LinearLayoutManager(requireContext())
        println("Datas : $listTransactions")
        rvTransaction.adapter = TransactionAdapter(listTransactions)

        val addButton = view.findViewById<FloatingActionButton>(R.id.add_transaction_button)
        addButton.setOnClickListener {
            findNavController().navigate(R.id.action_transactionsFragment_to_addTransactionFragment)
            val intent = Intent(requireContext(), AddTransactionActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}