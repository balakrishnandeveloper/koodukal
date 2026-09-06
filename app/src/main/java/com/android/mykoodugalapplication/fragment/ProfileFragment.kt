package com.android.mykoodugalapplication.fragment

import android.app.AlertDialog
import android.content.ContentValues
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import com.android.mykoodugalapplication.R
import com.android.mykoodugalapplication.databinding.FragmentProfileBinding

class ProfileFragment : Fragment() {
    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!
    private lateinit var imageUri: Uri


    private val galleryLauncher =
        registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->

            uri?.let {
                binding.imgProfile.setImageURI(it)
            }
        }

    private val cameraLauncher =
        registerForActivityResult(ActivityResultContracts.TakePicture()) { success ->

            if (success) {
                binding.imgProfile.setImageURI(imageUri)
            }
        }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        val view = binding.root
        binding.etUsername.setText("Balakrishnan")
        binding.etEmail.setText("user@gmail.com")
        binding.etMobile.setText("9876543210")
        binding.etLocation.setText("Chennai")

        binding.btnLogin.setOnClickListener {
            Toast.makeText(requireContext(), "Profile Saved", Toast.LENGTH_SHORT).show()
        }


        binding.imgEdit.setOnClickListener {
            showImagePickerDialog()
        }

        return view
    }

    private fun showImagePickerDialog() {

        val options = arrayOf("Camera", "Gallery")

        AlertDialog.Builder(context)
            .setTitle("Choose Image")
            .setItems(options) { _, which ->

                when (which) {

                    0 -> openCamera()

                    1 -> galleryLauncher.launch("image/*")
                }
            }
            .show()
    }

    private fun openCamera() {

        val values = ContentValues().apply {
            put(
                MediaStore.Images.Media.DISPLAY_NAME,
                "IMG_${System.currentTimeMillis()}.jpg"
            )
            put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg")
        }

        imageUri = requireContext().contentResolver.insert(
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
            values
        ) ?: return

        cameraLauncher.launch(imageUri)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}