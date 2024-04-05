package com.example.bondoman_pdd.ui.main

import android.annotation.SuppressLint
import android.content.Context.LOCATION_SERVICE
import android.content.Intent
import android.content.IntentFilter
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import android.widget.TextView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.getSystemService
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.bondoman_pdd.MainActivity
import com.example.bondoman_pdd.R
import com.example.bondoman_pdd.data.model.Transactions
import com.example.bondoman_pdd.data.transactions.setup.DatabaseHelper
import com.example.bondoman_pdd.databinding.ActivityAddTransactionBinding
import com.example.bondoman_pdd.ui.addTransactions.MyBroadcastReceiver
import com.example.bondoman_pdd.ui.settings.ACTION_RANDOMIZE_TRANSACTION
import java.text.SimpleDateFormat
import java.util.Calendar

class AddTransactionFragment : Fragment() {

    private lateinit var binding: ActivityAddTransactionBinding
    private lateinit var broadcastReceiver: MyBroadcastReceiver
    private var spinner: Spinner? = null


    companion object {
        fun newInstance() = AddTransactionFragment()
    }

//    private val viewModel: MainViewModel by viewModels()

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
                // Nothing to do here, as we'll set the prompt for the spinner
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

    @SuppressLint("CutPasteId", "SimpleDateFormat")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Find the logout button by its ID
        val saveTransactionButton = view.findViewById<Button>(R.id.save_add_transaction)

        val lokasi_textview = view.findViewById<TextView>(R.id.lokasi)
        if (ActivityCompat.checkSelfPermission(
                requireContext(),
                android.Manifest.permission.ACCESS_FINE_LOCATION
            ) != android.content.pm.PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                requireActivity(),
                arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION),
                1
            )
        } else {
            val locationManager = requireActivity().getSystemService(LOCATION_SERVICE) as LocationManager
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 5f, object : LocationListener {
                @SuppressLint("SetTextI18n")
                override fun onLocationChanged(location: Location) {
                    // Ambil kota
                    val geocoder = android.location.Geocoder(requireContext())
                    val addresses = geocoder.getFromLocation(location.latitude, location.longitude, 1)
                    val cityName = addresses?.get(0)?.locality
                    lokasi_textview.text = "$cityName"
                }
            })
        }

        // Set click listener on the logout button
        saveTransactionButton.setOnClickListener {
            // Masukan data ke database
            // Ambil data dari form
            val judul = view.findViewById<TextView>(R.id.judul).text.toString()
            val nominal = view.findViewById<TextView>(R.id.nominal).text.toString().toFloat()
            val kategori = view.findViewById<Spinner>(R.id.kategori).selectedItem.toString()
            val lokasi = view.findViewById<TextView>(R.id.lokasi).text.toString()

            // Untuk tanggal bisa menggunakan tanggal sekarang memakai Calendar.getInstance().time.toString()
            val timeNow = Calendar.getInstance().time
            val dateFormat = SimpleDateFormat("yyyy-MM-dd")
            val tanggal = dateFormat.format(timeNow).toString()
            println("Tanggal : $tanggal")

            // Insert data ke database
//            insertTransaction(Transactions(0, 123456789, judul, nominal, kategori, tanggal, lokasi))
            val db = DatabaseHelper(requireContext())
            val email = SecureStorage.getEmail(requireContext())
            // Ambil 8 karakter pertama dari email
            val id = email?.substring(0, 8)
            val nim = id?.toInt()
            println("NIM : $nim")
            db.insertTransaction(Transactions(0, nim!!, judul, nominal, kategori, tanggal, lokasi))
            // Show tabel transaksi di dalam logcat
            // Create an Intent to navigate back to LoginActivity

            val intent = Intent(requireActivity(), MainActivity::class.java)

            // Add flags to clear the activity stack so that LoginActivity becomes the top activity
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK

//            val randomize = view.findViewById<Button>(R.id.randomize_transaction)

//            binding = ActivityAddTransactionBinding.bind(view)

            //   Initialize the BroadcastReceiver
//            broadcastReceiver = MyBroadcastReceiver(binding)

//            // Register the BroadcastReceiver
//            val filter = IntentFilter().apply {
//                addAction(ACTION_RANDOMIZE_TRANSACTION)
//                addAction(Intent.ACTION_AIRPLANE_MODE_CHANGED)
//            }
//            val listenToBroadcastsFromOtherApps = true
//            val receiverFlags = if (listenToBroadcastsFromOtherApps) {
//                ContextCompat.RECEIVER_EXPORTED
//            } else {
//                ContextCompat.RECEIVER_NOT_EXPORTED
//            }
//            requireContext().registerReceiver(broadcastReceiver, filter, receiverFlags)


            // Start LoginActivity
            startActivity(intent)

            // Optionally, finish the current activity (fragment activity) if you want to clear it from the stack
            requireActivity().finish()
        }
    }
}
