package com.android.mykoodugalapplication.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.android.mykoodugalapplication.databinding.ActivityWelcomeBinding

class WelcomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityWelcomeBinding
    private var dX = 0f

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityWelcomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        intiView()
    }

    private fun intiView() {

        binding.sliderBtn.setOnTouchListener { view, event ->

            when (event.action) {

                android.view.MotionEvent.ACTION_DOWN -> {
                    dX = view.x - event.rawX
                }

                android.view.MotionEvent.ACTION_MOVE -> {
                    val newX = event.rawX + dX

                    // Limit movement inside parent
                    val maxX = binding.sliderLayout.width - view.width

                    if (newX >= 0 && newX <= maxX) {
                        view.x = newX
                    }
                }

                android.view.MotionEvent.ACTION_UP -> {

                    val maxX = binding.sliderLayout.width - view.width

                    if (view.x >= maxX * 0.8) {
                        // ✅ Slide completed → go next
                        startActivity(Intent(this, MainActivity::class.java))
                        finish()
                    } else {
                        // 🔁 Reset position
                        view.animate().x(maxX.toFloat()).setDuration(100).start()
                    }
                }
            }
            true
        }
    }
}