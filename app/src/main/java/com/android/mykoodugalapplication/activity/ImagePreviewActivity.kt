package com.android.mykoodugalapplication.activity

import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.android.mykoodugalapplication.databinding.ActivityImagePreviewBinding
import com.bumptech.glide.Glide

class ImagePreviewActivity : AppCompatActivity() {

    private lateinit var binding: ActivityImagePreviewBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityImagePreviewBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val image = intent.getStringExtra("image")

        image?.let {
            Glide.with(this)
                .load(Uri.parse(it))
                .into(binding.ivPreview)
        }

        binding.ivBack.setOnClickListener {
            finish()
        }
    }
}