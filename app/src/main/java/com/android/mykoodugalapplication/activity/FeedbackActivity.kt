package com.android.mykoodugalapplication.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.android.mykoodugalapplication.databinding.ActivityFeedbackBinding

class FeedbackActivity :AppCompatActivity() {

    private lateinit var binding: ActivityFeedbackBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityFeedbackBinding.inflate(layoutInflater)
        setContentView(binding.root)

        intiView()
    }

    private fun intiView(){
        binding.backIv.setOnClickListener {
            finish()
        }
    }

}