package com.android.mykoodugalapplication.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.android.mykoodugalapplication.databinding.ActivitySpottedSparrowAnywhereBinding

class NestInstalledActivity :AppCompatActivity(){

    private lateinit var binding:ActivitySpottedSparrowAnywhereBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivitySpottedSparrowAnywhereBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}