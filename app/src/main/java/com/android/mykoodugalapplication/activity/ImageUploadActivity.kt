package com.android.mykoodugalapplication.activity

import android.app.Activity
import android.content.ContentValues
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.android.mykoodugalapplication.adapter.ImageAdapter
import com.android.mykoodugalapplication.adapter.ImageUploadAdapter
import com.android.mykoodugalapplication.databinding.ActivityUploadImagesBinding

class ImageUploadActivity : AppCompatActivity() {

    private lateinit var binding: ActivityUploadImagesBinding
    private lateinit var adapter: ImageUploadAdapter

    private val imageList = ArrayList<String>()

    private lateinit var imageUri: Uri

    private val cameraLauncher =
        registerForActivityResult(ActivityResultContracts.TakePicture()) { success ->

            if (success) {

                if (imageList.size < 10) {

                    imageList.add(imageUri.toString())

                    adapter.notifyDataSetChanged()

                    updateNoDataView()
                }
            }
        }

    private val imagePicker =
        registerForActivityResult(ActivityResultContracts.GetMultipleContents()) { uris ->

            if (uris.isNotEmpty()) {

                for (uri in uris) {

                    if (imageList.size == 10)
                        break

                    imageList.add(uri.toString())
                }

                adapter.notifyDataSetChanged()

                updateNoDataView()
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityUploadImagesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initView()
    }

    private fun initView() {

        imageList.addAll(
            intent.getStringArrayListExtra("images")
                ?: arrayListOf()
        )

        binding.rvImages.layoutManager =
            GridLayoutManager(this, 3)

        adapter = ImageUploadAdapter(this, imageList) { position ->

            imageList.removeAt(position)

            adapter.notifyItemRemoved(position)

            updateNoDataView()
        }

        binding.rvImages.adapter = adapter
        updateNoDataView()

        binding.btnAddImage.setOnClickListener {

            showImagePickerDialog()
        }

        binding.btnDone.setOnClickListener {

            val intent = Intent()

            intent.putStringArrayListExtra(
                "images",
                imageList
            )

            setResult(Activity.RESULT_OK, intent)

            finish()
        }

        binding.backIv.setOnClickListener {
            finish()
        }
    }

    private fun showImagePickerDialog() {

        val options = arrayOf("Camera", "Gallery")

        androidx.appcompat.app.AlertDialog.Builder(this)
            .setTitle("Select Image")
            .setItems(options) { _, which ->

                when (which) {

                    0 -> {
                        openCamera()
                    }

                    1 -> {
                        imagePicker.launch("image/*")
                    }
                }

            }
            .show()
    }

    private fun openCamera() {

        val values = ContentValues().apply {
            put(MediaStore.Images.Media.DISPLAY_NAME, "IMG_${System.currentTimeMillis()}.jpg")
            put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg")
        }

        imageUri = contentResolver.insert(
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
            values
        )!!

        cameraLauncher.launch(imageUri)
    }

    private fun updateNoDataView() {

        if (imageList.isEmpty()) {
            binding.tvNoData.visibility = View.VISIBLE
            binding.rvImages.visibility = View.GONE
        } else {
            binding.tvNoData.visibility = View.GONE
            binding.rvImages.visibility = View.VISIBLE
        }

        binding.tvCount.text = "${imageList.size} / 10 Images"
    }
}