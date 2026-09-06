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
import com.android.mykoodugalapplication.adapter.BannerSliderAdapter
import com.android.mykoodugalapplication.adapter.SliderAdapter
import com.android.mykoodugalapplication.commonUtils.PreferenceManager
import com.android.mykoodugalapplication.dataClass.BannerImage
import com.android.mykoodugalapplication.dataClass.SliderModel
import com.android.mykoodugalapplication.viwemodel.LoginViewModel
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class HomeFragment : Fragment(R.layout.fragment_home) {

    private lateinit var viewPager: ViewPager2
    private lateinit var txtDate: TextView
    private lateinit var txtLocation: TextView
    private lateinit var txtTotalNest: TextView
    private lateinit var txtActiveNest: TextView
    private lateinit var txtEggs: TextView
    private lateinit var txtChicks: TextView
    private lateinit var txtLeaderboard: TextView
    private lateinit var layoutSlideBtn: LinearLayout
    private lateinit var txtSlide: TextView

    private val handler = Handler(Looper.getMainLooper())
    private var autoScrollRunnable: Runnable? = null
    private lateinit var preference: PreferenceManager

    private val viewModel by lazy {
        ViewModelProvider(this)[LoginViewModel::class.java]
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewPager       = view.findViewById(R.id.viewPagerSlider)
        txtDate         = view.findViewById(R.id.txtDate)
        txtLocation     = view.findViewById(R.id.txtLocation)
        txtTotalNest    = view.findViewById(R.id.txtTotalNest)
        txtActiveNest   = view.findViewById(R.id.txtActiveNest)
        txtEggs         = view.findViewById(R.id.txtEggs)
        txtChicks       = view.findViewById(R.id.txtChicks)
        txtLeaderboard  = view.findViewById(R.id.txtLeaderboard)
        layoutSlideBtn  = view.findViewById(R.id.layoutSlideBtn)
        txtSlide        = view.findViewById(R.id.txtSlide)

        preference = PreferenceManager(requireContext())

        showDateTime()
        setupStaticSlider()      // show local images while API loads
        setupClickListeners()
        observeDashboard()

        // trigger API call on fragment enter
        viewModel.getDashboard(preference.getUserId(), preference.getToken())
    }

    private fun setupClickListeners() {
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

    private fun setupStaticSlider() {
        val list = listOf(
            SliderModel(R.drawable.sparrow1, "Save Sparrows Save Nature"),
            SliderModel(R.drawable.sparrow2, "Provide Water for Birds"),
            SliderModel(R.drawable.sparrow3, "Plant Trees for Birds")
        )
        viewPager.adapter = SliderAdapter(list)
        startAutoScroll(list.size)
    }

    private fun setupApiBannerSlider(banners: List<BannerImage>) {
        stopAutoScroll()
        viewPager.adapter = BannerSliderAdapter(banners)
        startAutoScroll(banners.size)
    }

    private fun startAutoScroll(itemCount: Int) {
        stopAutoScroll()
        if (itemCount < 2) return
        autoScrollRunnable = object : Runnable {
            override fun run() {
                val next = (viewPager.currentItem + 1) % itemCount
                viewPager.currentItem = next
                handler.postDelayed(this, 3000)
            }
        }
        handler.postDelayed(autoScrollRunnable!!, 3000)
    }

    private fun stopAutoScroll() {
        autoScrollRunnable?.let { handler.removeCallbacks(it) }
        autoScrollRunnable = null
    }

    override fun onDestroyView() {
        super.onDestroyView()
        stopAutoScroll()
    }

    private fun showDateTime() {
        val sdfDate = SimpleDateFormat("dd MMM yyyy", Locale.getDefault())
        txtDate.text = "Date: ${sdfDate.format(Date())}"
    }

    private fun observeDashboard() {
        viewModel.dashDetailsResponse.observe(viewLifecycleOwner) { response ->
            if (response == null) return@observe

            if (response.status == "1") {

                // bind statistics to UI
                response.statistics?.let { stats ->
                    txtLocation.text   = "📍 ${stats.location ?: "-"}"
                    txtTotalNest.text  = stats.totalNests?.toString() ?: "0"
                    txtActiveNest.text = stats.activeNests?.toString() ?: "0"
                    txtEggs.text       = stats.eggs?.toString() ?: "0"
                    txtChicks.text     = stats.chicks?.toString() ?: "0"
                    txtLeaderboard.text = "🏆 Top District: ${stats.topDistrict ?: "-"}"
                }

                // replace static slider with API banners
                response.images?.let { banners ->
                    if (banners.isNotEmpty()) setupApiBannerSlider(banners)
                }

            } else {
                Toast.makeText(
                    context,
                    response.message ?: "Failed to load dashboard",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }
}
