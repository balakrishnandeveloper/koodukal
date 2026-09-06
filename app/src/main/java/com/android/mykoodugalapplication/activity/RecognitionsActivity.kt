package com.android.mykoodugalapplication.activity

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.android.mykoodugalapplication.R
import com.android.mykoodugalapplication.adapter.ProjectAdapter
import com.android.mykoodugalapplication.dataClass.RecognitionItem
import com.android.mykoodugalapplication.databinding.ActivityProjectsBinding

class RecognitionsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityProjectsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityProjectsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        intiView()
      adapterCall()
    }

    private fun adapterCall(){
        binding.rgRv.layoutManager =
            GridLayoutManager(this, 1)

        val list = listOf(
            RecognitionItem("", R.drawable.news1),
            RecognitionItem("", R.drawable.news2),
            RecognitionItem("", R.drawable.news3),
            RecognitionItem("", R.drawable.news4))

        binding.rgRv.adapter = ProjectAdapter(list){
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
