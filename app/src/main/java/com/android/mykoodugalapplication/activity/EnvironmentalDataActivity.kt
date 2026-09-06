package com.android.mykoodugalapplication.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.android.mykoodugalapplication.R
import com.android.mykoodugalapplication.databinding.FragmentEnvironmentalDataBinding

class EnvironmentalDataActivity :AppCompatActivity() {

    private lateinit var binding:FragmentEnvironmentalDataBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=FragmentEnvironmentalDataBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initView()
    }

    private fun initView(){
        binding.btnNext.setOnClickListener {

            val water = when (binding.rgWater.checkedRadioButtonId) {
                R.id.rbWaterYes -> "Yes"
                else -> "No"
            }
        }

        binding.backIv.setOnClickListener {
            finish()
        }

        binding.btnNext.setOnClickListener {
            finish()
        }
    }
}