package com.android.mykoodugalapplication.activity

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Geocoder
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.android.mykoodugalapplication.activity.ImageUploadActivity
import com.android.mykoodugalapplication.databinding.ActivitySparrowSpottedBinding
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class SparrowSpottedActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySparrowSpottedBinding
    private lateinit var fusedLocationClient: FusedLocationProviderClient

    // Stores uploaded image Uris as Strings
    private var imageList = ArrayList<String>()

    private val permissionLauncher =
        registerForActivityResult(
            ActivityResultContracts.RequestPermission()) { granted ->
            if (granted) {
                getCurrentLocation()
            }
        }

    companion object {
        const val REQUEST_UPLOAD_IMAGES = 1001
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySparrowSpottedBinding.inflate(layoutInflater)
        setContentView(binding.root)

        intiView()

    }

    private fun intiView(){
        if (ContextCompat.checkSelfPermission(
                this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            getCurrentLocation()
        } else {
            permissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
        }
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        setCurrentDateTime()

        // TODO: Replace with your GPS method
        binding.etLocation.setText("Fetching location...")

        binding.uploadBt.setOnClickListener {

            val intent = Intent(this, ImageUploadActivity::class.java)
            intent.putStringArrayListExtra("images", imageList)
            startActivityForResult(intent, REQUEST_UPLOAD_IMAGES)
        }

        binding.backIv.setOnClickListener {
            finish()
        }

        binding.tvViewImages.setOnClickListener {

            if (imageList.isEmpty())
                return@setOnClickListener

            val intent = Intent(this, ImageUploadActivity::class.java)
            intent.putStringArrayListExtra("images", imageList)
            startActivityForResult(intent, REQUEST_UPLOAD_IMAGES)
        }

        binding.btnLogin.setOnClickListener {

            if (imageList.isEmpty()) {

                binding.tvImageCount.error = "Upload at least one image"
                return@setOnClickListener
            }
            finish()
        }
    }

    private fun setCurrentDateTime() {

        val sdf =
            SimpleDateFormat("dd-MM-yyyy hh:mm a", Locale.getDefault())

        binding.etAbandoned.setText(sdf.format(Date()))
    }

    override fun onActivityResult(
        requestCode: Int,
        resultCode: Int,
        data: Intent?
    ) {

        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == REQUEST_UPLOAD_IMAGES &&
            resultCode == Activity.RESULT_OK
        ) {

            imageList =
                data?.getStringArrayListExtra("images")
                    ?: ArrayList()

            binding.tvImageCount.text =
                imageList.size.toString()
        }

    }

    @SuppressLint("MissingPermission")
    private fun getCurrentLocation() {

        fusedLocationClient.lastLocation.addOnSuccessListener { location ->

            if (location != null) {

                val geocoder = Geocoder(this, Locale.getDefault())

                try {

                    val addresses = geocoder.getFromLocation(
                        location.latitude,
                        location.longitude,
                        1
                    )

                    if (!addresses.isNullOrEmpty()) {

                        val address = addresses[0]

                        val fullAddress = buildString {
                            append(address.getAddressLine(0))
                        }

                        binding.etLocation.setText(fullAddress)
                    }

                } catch (e: Exception) {
                    e.printStackTrace()
                }

            } else {

                binding.etLocation.setText("Location not available")
            }
        }
    }

}