package com.example.bondoman_pdd.ui.chart

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.bondoman_pdd.R
import com.example.bondoman_pdd.data.transactions.setup
import com.example.bondoman_pdd.databinding.FragmentChartBinding
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.utils.ColorTemplate

class ChartFragment : Fragment() {

    private var _binding: FragmentChartBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val chartViewModel =
            ViewModelProvider(this).get(ChartViewModel::class.java)

        _binding = FragmentChartBinding.inflate(inflater, container, false)
        val root: View = binding.root

//        val textView: TextView = binding.textChart
//        chartViewModel.text.observe(viewLifecycleOwner) {
//            textView.text = it
//        }
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Membuat chart ditaru di id 'pieChart'
        val pieChar : PieChart = view.findViewById(R.id.pieChart)
        val entries : ArrayList<PieEntry> = ArrayList()

        val setupData = setup.DatabaseHelper(requireContext())
        val totalPemasukan : Float = setupData.getTotalPemasukan(13521028)
        val totalPengeluaran : Float = setupData.getTotalPengeluaran(13521028)
        println("Pemasukan : $totalPemasukan")
        println("Pengeluaran : $totalPengeluaran")
        entries.add(PieEntry(totalPemasukan, "Pemasukan"))
        entries.add(PieEntry(totalPengeluaran, "Pengeluaran"))

        // Membuat data set
        val dataSet = PieDataSet(entries, "Election Results")
        dataSet.setColors(*ColorTemplate.COLORFUL_COLORS)

        // Membuat data
        val data = PieData(dataSet)
        pieChar.data = data
        pieChar.invalidate()
        println("ChartFragment.onCreateView")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}