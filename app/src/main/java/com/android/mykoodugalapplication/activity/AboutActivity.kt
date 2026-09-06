package com.android.mykoodugalapplication.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.android.mykoodugalapplication.databinding.ActivityAboutUsBinding

class AboutActivity: AppCompatActivity()  {

    private lateinit var binding: ActivityAboutUsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityAboutUsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        intiView()
    }

    private fun intiView(){
        binding.backIv.setOnClickListener {
            finish()
        }
    }
}