package com.example.bondoman_pdd.ui.scanner

import ScannerViewModel
import SecureStorage
import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.bondoman_pdd.data.repository.ScannerRepository
import com.example.bondoman_pdd.databinding.FragmentScannerBinding

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
        scannerViewModel = ViewModelProvider(
            this,
            ScannerViewModelFactory(ScannerRepository())
        )[ScannerViewModel::class.java]

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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        checkPermissions()
        // Initialize ScannerViewModel and other setup...
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
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

    private fun openCamera() {
        val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE)
    }

    private fun openGallery() {
        val pickPhotoIntent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        startActivityForResult(pickPhotoIntent, REQUEST_PICK_IMAGE)
    }


    private fun uploadImage(bitmap: Bitmap) {
        // Convert Bitmap to MultipartBody.Part as shown in the previous response
        // Assuming ScannerViewModel is properly initialized
        val token = SecureStorage.getToken(requireContext())
        if (token != null) {
            scannerViewModel.uploadImage(bitmap, token)
        }
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


    private fun checkPermissions() {
        val requiredPermissions = arrayOf(
            Manifest.permission.CAMERA,
            Manifest.permission.READ_EXTERNAL_STORAGE
        )

        val permissionsNotGranted = requiredPermissions.filter {
            ContextCompat.checkSelfPermission(requireContext(), it) != PackageManager.PERMISSION_GRANTED
        }.toTypedArray()

        if (permissionsNotGranted.isNotEmpty()) {
            requestPermissions(permissionsNotGranted, PERMISSIONS_REQUEST_CODE)
        }
    }

    companion object {
        private const val PERMISSIONS_REQUEST_CODE = 123
        private const val REQUEST_IMAGE_CAPTURE = 1
        private const val REQUEST_PICK_IMAGE = 2
    }


//    companion object {
//        private const val REQUEST_IMAGE_CAPTURE = 100
//        private const val REQUEST_PICK_IMAGE = 101
//    }
}