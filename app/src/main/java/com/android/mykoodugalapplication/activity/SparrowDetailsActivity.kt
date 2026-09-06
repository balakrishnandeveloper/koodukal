package com.android.mykoodugalapplication.activity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.android.mykoodugalapplication.commonUtils.Common
import com.android.mykoodugalapplication.databinding.FragmentNestDetailsBinding

class SparrowDetailsActivity :AppCompatActivity() {

    private lateinit var binding:FragmentNestDetailsBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=FragmentNestDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        intiview()
    }

    private fun intiview(){

        binding.btnNext.setOnClickListener {
            val intent= Intent(this,SparrowOccupationStatusActivity::class.java)
            startActivity(intent)
        }

        binding.backIv.setOnClickListener {
            finish()
        }

        binding.etDate.setOnClickListener {
            Common.showDatePicker(this) { selectedDate ->
                binding.etDate.text = selectedDate

            }
        }
    }

}