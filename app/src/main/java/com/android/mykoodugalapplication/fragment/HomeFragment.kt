package com.android.mykoodugalapplication.fragment

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.android.mykoodugalapplication.R
import com.android.mykoodugalapplication.activity.MainActivity
import com.android.mykoodugalapplication.adapter.SliderAdapter
import com.android.mykoodugalapplication.commonUtils.PreferenceManager
import com.android.mykoodugalapplication.dataClass.SliderModel
import com.android.mykoodugalapplication.viwemodel.LoginViewModel
import com.onesignal.CallbackThreadManager.Companion.preference
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

class HomeFragment : Fragment(R.layout.fragment_home) {

    private lateinit var viewPager: ViewPager2
    private lateinit var txtDate: TextView
    private lateinit var txtTime: TextView
    private val handler = Handler(Looper.getMainLooper())
    private lateinit var runnable: Runnable
    private lateinit var txtSlide: TextView
    private lateinit var layoutSlideBtn: LinearLayout
    private lateinit var preference: PreferenceManager


    private val viewModel by lazy {
        ViewModelProvider(this)[LoginViewModel::class.java]
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewPager = view.findViewById(R.id.viewPagerSlider)
        txtDate = view.findViewById(R.id.txtDate)
        txtTime = view.findViewById(R.id.txtTime)
        layoutSlideBtn = view.findViewById(R.id.layoutSlideBtn)
        txtSlide = view.findViewById(R.id.txtSlide)
        preference = PreferenceManager(requireContext())
        val userId = preference.getUserId()
        val token = preference.getToken()

        viewModel.getDashboard(userId, token)

        setTimeBasedImage()
        setupSlider()
        showDateTime()
        clcikable()
        observer()
    }

    private fun clcikable() {
        layoutSlideBtn.setOnClickListener {

            handler.postDelayed({
                parentFragmentManager.beginTransaction()
                    .replace(R.id.fragmentContainer, NestInstalledFragment())
                    .addToBackStack(null)
                    .commit()
                (activity as MainActivity).setBottomNavSelection(R.id.nav_nest)
            }, 200)
        }
    }


    private fun setTimeBasedImage() {

        val calendar = Calendar.getInstance()
        val hour = calendar.get(Calendar.HOUR_OF_DAY)

    }

    private fun setupSlider() {

        val list = listOf(
            SliderModel(R.drawable.sparrow1, "Save Sparrows Save Nature"),
            SliderModel(R.drawable.sparrow2, "Provide Water for Birds"),
            SliderModel(R.drawable.sparrow3, "Plant Trees for Birds")
        )

        val adapter = SliderAdapter(list)
        viewPager.adapter = adapter

        runnable = Runnable {

            val current = viewPager.currentItem

            if (current == list.size - 1) {
                viewPager.currentItem = 0
            } else {
                viewPager.currentItem = current + 1
            }
        }

        handler.postDelayed(object : Runnable {
            override fun run() {
                runnable.run()
                handler.postDelayed(this, 3000)
            }
        }, 3000)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        handler.removeCallbacksAndMessages(null)
    }

    private fun showDateTime(){

        val sdfDate = SimpleDateFormat("dd MMM yyyy", Locale.getDefault())
        val sdfTime = SimpleDateFormat("HH:mm:ss", Locale.getDefault())

        val currentDate = sdfDate.format(Date())
        val currentTime = sdfTime.format(Date())

        txtDate.text = "Date: $currentDate"
        txtTime.text = "Time: $currentTime"
    }

    private fun observer(){
        viewModel.dashDetailsResponse.observe(viewLifecycleOwner) { response ->

            if (response.status == "1") {
                Toast.makeText(context, response.message, Toast.LENGTH_SHORT).show()

            }
        }
    }

}
