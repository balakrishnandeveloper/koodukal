package com.android.mykoodugalapplication.activity

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.android.mykoodugalapplication.R
import com.android.mykoodugalapplication.adapter.MediaAdapter
import com.android.mykoodugalapplication.dataClass.RecognitionItem
import com.android.mykoodugalapplication.databinding.ActivityProjectsBinding

class MediaActivity :AppCompatActivity() {
    private lateinit var binding: ActivityProjectsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityProjectsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        intiView()
        adapterCall()
    }

    private fun adapterCall(){

        binding.titleTxt.text="Media Partners"

        binding.rgRv.layoutManager =
            GridLayoutManager(this, 3)

        val list = listOf(
            RecognitionItem("", R.drawable.tamil_news_iv),
            RecognitionItem("", R.drawable.puthiya_iv),
            RecognitionItem("", R.drawable.tamil_news2),
            RecognitionItem("", R.drawable.news7_iv),
            RecognitionItem("", R.drawable.newsj_iv),
            RecognitionItem("", R.drawable.zee_iv),
            RecognitionItem("", R.drawable.tamil_news_iv),
            RecognitionItem("", R.drawable.puthiya_iv),
            RecognitionItem("", R.drawable.tamil_news2),
            RecognitionItem("", R.drawable.news7_iv),
            RecognitionItem("", R.drawable.newsj_iv),
            RecognitionItem("", R.drawable.zee_iv)
        )

        binding.rgRv.adapter = MediaAdapter(list){
            startActivity(
                Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse("https://www.google.com/search?q=koodugal+trust")
                )
            )
        }
    }
    private fun intiView(){
        binding.backIv.setOnClickListener {
            finish()
        }
    }
}