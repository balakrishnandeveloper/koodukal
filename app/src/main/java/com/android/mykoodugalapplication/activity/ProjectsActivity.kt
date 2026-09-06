package com.android.mykoodugalapplication.activity

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.android.mykoodugalapplication.R
import com.android.mykoodugalapplication.adapter.RecognitionAdapter
import com.android.mykoodugalapplication.dataClass.RecognitionItem
import com.android.mykoodugalapplication.databinding.ActivityProjectsBinding

class ProjectsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityProjectsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityProjectsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        intiView()
        adapterCall()
    }

    private fun intiView(){
        binding.backIv.setOnClickListener {
            finish()
        }
    }


    private fun adapterCall(){

        binding.titleTxt.text="Projects"

        binding.rgRv.layoutManager =
            GridLayoutManager(this, 1)

        val list = listOf(
            RecognitionItem("Sparrow Conservation", R.drawable.home_iv),
            RecognitionItem("Water Management", R.drawable.water_iv),
            RecognitionItem("Waste Management", R.drawable.waste_iv),
            RecognitionItem("Bio Gas", R.drawable.gas_iv),
            RecognitionItem("String Art", R.drawable.string_iv),
            RecognitionItem("koodugal Agriculture", R.drawable.agricultrure_iv),
            RecognitionItem("Explore Yourself", R.drawable.yourself_iv),
            RecognitionItem("Digitalizing Rural India", R.drawable.rural_iv),
            RecognitionItem("Bird Sanctuary by Planting Native Trees", R.drawable.birds_tree_iv))

        binding.rgRv.adapter = RecognitionAdapter(list){
            startActivity(
                Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse("https://www.google.com/search?q=koodugal+trust")
                )
            )
        }
    }

}
