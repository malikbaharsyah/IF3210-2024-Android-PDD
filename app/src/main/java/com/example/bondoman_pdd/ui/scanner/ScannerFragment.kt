package com.example.bondoman_pdd.ui.scanner

import SecureStorage
import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.bondoman_pdd.data.model.ItemsInfo
import com.example.bondoman_pdd.data.model.Transactions
import com.example.bondoman_pdd.data.transactions.setup
import com.example.bondoman_pdd.databinding.FragmentScannerBinding
import com.example.bondoman_pdd.ui.login.LoginActivity
import java.text.SimpleDateFormat
import java.util.Calendar

class ScannerFragment : Fragment() {

    private var _binding: FragmentScannerBinding? = null
    private val binding get() = _binding!!

    private lateinit var imageView: ImageView
    private lateinit var buttonCamera: Button
    private lateinit var buttonGallery: Button
    private lateinit var scannerViewModel: ScannerViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        scannerViewModel = ViewModelProvider(this).get(ScannerViewModel::class.java)

        _binding = FragmentScannerBinding.inflate(inflater, container, false)
        val root: View = binding.root

        imageView = binding.imageView
        buttonCamera = binding.buttonCamera
        buttonGallery = binding.buttonGallery

        buttonCamera.setOnClickListener {
            openCamera()
        }

        buttonGallery.setOnClickListener {
            openGallery()
        }

        return root
    }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        checkPermissions()

        scannerViewModel.scanResult.observe(viewLifecycleOwner) { result ->
            result?.fold(onSuccess = { response ->
                insertItemsToDatabase(response.items.items)
            }, onFailure = {
                showTokenExpired()
            })
        }
        scannerViewModel.clearResult()
    }

    @SuppressLint("SimpleDateFormat")
    private fun insertItemsToDatabase(items: List<ItemsInfo>) {
        val db = setup.DatabaseHelper(requireContext())
        val email = SecureStorage.getEmail(requireContext())
        if (email != null) {
            val id = email.substring(0, 8)
            val nim = id.toInt()
            val timeNow = Calendar.getInstance().time
            val dateFormat = SimpleDateFormat("yyyy-MM-dd")
            val tanggal = dateFormat.format(timeNow)

            items.forEach { item ->
                db.insertTransaction(Transactions(0, nim, item.name, item.price, "Penjualan", tanggal, "kos joel"))
            }
            Toast.makeText(context, "Bill read success", Toast.LENGTH_LONG).show()
        } else {
            Log.e("ScannerFragment", "User not logged in")
            Toast.makeText(context, "Failed, user not logged in.", Toast.LENGTH_LONG).show()
        }
    }


    private fun openCamera() {
        val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE)
    }

    private fun openGallery() {
        val pickPhotoIntent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        startActivityForResult(pickPhotoIntent, REQUEST_PICK_IMAGE)
    }

    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                REQUEST_IMAGE_CAPTURE -> {
                    val imageBitmap = data?.extras?.get("data") as Bitmap
                    imageView.setImageBitmap(imageBitmap)
                    uploadImage(imageBitmap)
                }
                REQUEST_PICK_IMAGE -> {
                    val imageUri = data?.data
                    imageView.setImageURI(imageUri)
                    imageUri?.let { uri ->
                        val bitmap = MediaStore.Images.Media.getBitmap(requireContext().contentResolver, uri)
                        uploadImage(bitmap)
                    }
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    @Deprecated("Deprecated in Java")
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == PERMISSIONS_REQUEST_CODE && grantResults.all { it == PackageManager.PERMISSION_GRANTED }) {
            // Permissions granted, you can safely open camera or gallery
        } else {
            // Handle permission denial
            Toast.makeText(context, "Permissions are required to proceed.", Toast.LENGTH_LONG).show()
        }
    }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    private fun checkPermissions() {
        val requiredPermissions = arrayOf(
            Manifest.permission.CAMERA,
            Manifest.permission.READ_MEDIA_IMAGES
        )

        val permissionsNotGranted = requiredPermissions.filter {
            ContextCompat.checkSelfPermission(requireContext(), it) != PackageManager.PERMISSION_GRANTED
        }.toTypedArray()

        if (permissionsNotGranted.isNotEmpty()) {
            requestPermissions(permissionsNotGranted, PERMISSIONS_REQUEST_CODE)
        }
    }

    private fun uploadImage(bitmap: Bitmap) {
        val userToken = SecureStorage.getToken(requireContext())
        if (userToken != null) {
            scannerViewModel.uploadImage(bitmap, userToken)
        }
    }

    private fun showTokenExpired() {
        AlertDialog.Builder(requireContext()).apply {
            setTitle("Session Expired")
            setMessage("Scan failed. Your session has expired. Please log in again.")
            setPositiveButton("OK") { _, _ ->
                SecureStorage.deleteToken(requireContext())
                SecureStorage.deleteEmail(requireContext())
                Log.d("ScannerFragment","${SecureStorage.getToken(requireContext())} token removed")
                val intent = Intent(requireActivity(), LoginActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(intent)
                requireActivity().finish()
            }
            setCancelable(false) // Prevent the dialog from being dismissed without user action
            show() // Show the dialog to the user
        }
    }

    companion object {
        private const val REQUEST_IMAGE_CAPTURE = 1
        private const val REQUEST_PICK_IMAGE = 2
        private const val PERMISSIONS_REQUEST_CODE = 123
    }
}