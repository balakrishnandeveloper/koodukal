package com.android.mykoodugalapplication.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.android.mykoodugalapplication.R

class EnvironmentalDataFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_environmental_data, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val btnSubmit = view.findViewById<Button>(R.id.btnNext)

        val rgWater = view.findViewById<RadioGroup>(R.id.rgWater)
        val rgNoise = view.findViewById<RadioGroup>(R.id.rgNoise)

        btnSubmit.setOnClickListener {

            val water = when (rgWater.checkedRadioButtonId) {
                R.id.rbWaterYes -> "Yes"
                else -> "No"
            }

            val noiseId = rgNoise.checkedRadioButtonId
            val noise = view.findViewById<RadioButton>(noiseId)?.text.toString()

            // 👉 You can store or pass data here
            Toast.makeText(requireContext(),
                "Water: $water, Noise: $noise",
                Toast.LENGTH_SHORT).show()
        }
    }
}