package com.android.mykoodugalapplication.activity

import android.Manifest
import android.content.ContentValues
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.android.mykoodugalapplication.R
import com.android.mykoodugalapplication.commonUtils.Common
import com.android.mykoodugalapplication.databinding.ActivityRegisterBinding
import com.android.mykoodugalapplication.viwemodel.LoginViewModel
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File
import java.io.FileOutputStream

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding
    private var selectedImageUri: Uri? = null

    private val viewModel by lazy {
        ViewModelProvider(this)[LoginViewModel::class.java]
    }


    private val pickImageLauncher = registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
        uri?.let {
            selectedImageUri = it
            binding.imgProfile.setImageURI(it)
        }
    }

    // Camera capture
    private var cameraImageUri: Uri? = null
    private val takePictureLauncher = registerForActivityResult(ActivityResultContracts.TakePicture()) { success ->
        if (success) {
            cameraImageUri?.let {
                selectedImageUri = it
                binding.imgProfile.setImageURI(it)
            }
        }
    }

    private val REQUIRED_PERMISSIONS = mutableListOf<String>().apply {
        add(Manifest.permission.CAMERA)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            add(Manifest.permission.READ_MEDIA_IMAGES)
        } else {
            add(Manifest.permission.READ_EXTERNAL_STORAGE)
            add(Manifest.permission.WRITE_EXTERNAL_STORAGE)
        }
    }.toTypedArray()

    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        val allGranted = permissions.entries.all { it.value == true }
        if (allGranted) {
            // Permissions granted, open Camera or Gallery
            showImagePickerDialog()
        } else {
            Toast.makeText(this, "Permissions required for camera/gallery", Toast.LENGTH_SHORT).show()
        }
    }

    private fun checkPermissionsAndOpenPicker() {
        val missingPermissions = REQUIRED_PERMISSIONS.filter {
            ContextCompat.checkSelfPermission(this, it) != PackageManager.PERMISSION_GRANTED
        }
        if (missingPermissions.isEmpty()) {
            showImagePickerDialog()
        } else {
            requestPermissionLauncher.launch(REQUIRED_PERMISSIONS)
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        intiView()
        observer()
        observerMobile()
    }

    private fun  intiView(){

        binding.etPassword.setOnClickListener {
            Common.showDatePicker(this) { selectedDate ->
                binding.etPassword.setText(selectedDate)

            }
        }

        binding.etSpecification.setOnClickListener {

            val roles = arrayOf(
                "Student",
                "Employee",
                "Business Owner",
                "Researcher",
                "Bird Watcher",
                "Wildlife Photographer",
                "Nature Enthusiast",
                "Veterinarian",
                "Other"
            )

            AlertDialog.Builder(this)
                .setTitle("Select Your Role")
                .setItems(roles) { _, which ->
                    binding.etSpecification.setText(roles[which])
                }
                .show()
        }

        binding.btnLogin.setOnClickListener {

            val name = binding.etUsername.text.toString().trim()
            val email = binding.etEmail.text.toString().trim()
            val mobile = binding.etMobile.text.toString().trim()
            val dob = binding.etPassword.text.toString().trim() // Rename to etDob if possible
            val location = binding.etLocation.text.toString().trim()
            val role = binding.etSpecification.text.toString().trim()

            val imagePart = selectedImageUri?.let {
                getImagePart(it)
            }

            binding.etMobile.addTextChangedListener(object : TextWatcher {

                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

                override fun afterTextChanged(s: Editable?) {

                    val mobile = s.toString()

                    if (mobile.length == 10) {

                        binding.progressBar.visibility = View.VISIBLE

                        viewModel.verifyMobile(mobile)
                    } else {

                        binding.tvMobileStatus.visibility = View.GONE
                    }
                }
            })

            when {
                name.isEmpty() -> {
                    Toast.makeText(this, "Please enter your name", Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }

                email.isEmpty() -> {
                    Toast.makeText(this, "Please enter your email", Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }

                !isValidEmail(email) -> {
                    Toast.makeText(this, "Please enter a valid email", Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }

                mobile.isEmpty() -> {
                    Toast.makeText(this, "Please enter your mobile number", Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }

                !isValidMobile(mobile) -> {
                    Toast.makeText(this, "Please enter a valid mobile number", Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }

                dob.isEmpty() -> {
                    Toast.makeText(this, "Please select your date of birth", Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }

                location.isEmpty() -> {
                    Toast.makeText(this, "Please enter your location", Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }

                role.isEmpty() -> {
                    Toast.makeText(this, "Please enter your Role", Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }

            }

            // All validations passed
            binding.progressBar.visibility = View.VISIBLE

            viewModel.registerRequest(
                name,
                email,
                mobile,
                dob,
                location,
                role,
                imagePart
            )
        }


        binding.profileContainer.setOnClickListener {
            checkPermissionsAndOpenPicker()
        }

        binding.backIv.setOnClickListener {
            finish()
        }

    }

    private fun showImagePickerDialog() {
        val options = arrayOf("Camera", "Gallery")
        AlertDialog.Builder(this)
            .setTitle("Select Image")
            .setItems(options) { _, which ->
                when (which) {
                    0 -> openCamera()
                    1 -> openGallery()
                }
            }
            .show()
    }

    private fun openGallery() {
        pickImageLauncher.launch("image/*")
    }

    private fun openCamera() {
        val uri = createImageUri()
        cameraImageUri = uri
        takePictureLauncher.launch(uri)
    }

    private fun createImageUri(): Uri {
        val resolver = contentResolver
        val contentValues = ContentValues().apply {
            put(MediaStore.Images.Media.DISPLAY_NAME, "profile_${System.currentTimeMillis()}.jpg")
            put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg")
        }
        return resolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)!!
    }

    private fun isValidEmail(email: String): Boolean {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    private fun isValidMobile(mobile: String): Boolean {
        return mobile.length == 10 && mobile.all { it.isDigit() }
    }

    private fun observer() {
        viewModel.registerDetailsResponse.observe(this) {
            binding.progressBar.visibility = View.GONE
            if (it != null) {
                val message = it.message
                val status = it.status.toString()
                if (status == "1") {
                    val intent = Intent(this, OtpActivity::class.java)
                    intent.putExtra("user_input", binding.etEmail.text.toString().trim())
                    intent.putExtra("type","Register")
                    startActivity(intent)
                    finish()
                    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()

                }else {
                    Toast.makeText(applicationContext, it.message, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }


    private fun observerMobile(){
        viewModel.mobileDetailsResponse.observe(this) { response ->

            binding.progressBar.visibility = View.GONE

            binding.tvMobileStatus.visibility = View.VISIBLE

            if (response.status=="1") {
                binding.tvMobileStatus.text = "✓ Mobile number verified"
                binding.tvMobileStatus.setTextColor(
                    ContextCompat.getColor(this, R.color.white))

            } else {
                binding.tvMobileStatus.text = "✗ Mobile number already registered"
                binding.tvMobileStatus.setTextColor(
                    ContextCompat.getColor(this, R.color.link_color)
                )
            }
        }
    }

    private fun getImagePart(uri: Uri): MultipartBody.Part {

        val inputStream = contentResolver.openInputStream(uri)!!

        val file = File(cacheDir, "profile_image.jpg")

        FileOutputStream(file).use { output ->
            inputStream.copyTo(output)
        }

        val requestFile = file.asRequestBody("image/*".toMediaTypeOrNull())

        return MultipartBody.Part.createFormData(
            "profile_image",
            file.name,
            requestFile
        )
    }


}