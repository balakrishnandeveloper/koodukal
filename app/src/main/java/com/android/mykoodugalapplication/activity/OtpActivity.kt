package com.android.mykoodugalapplication.activity

import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.mykoodugalapplication.databinding.OtpScreenLayoutBinding

class OtpActivity : AppCompatActivity() {

    private lateinit var binding: OtpScreenLayoutBinding
//    private lateinit var viewModel: OtpViewModel

    private val timerDuration = 60000L
    private lateinit var countDownTimer: CountDownTimer
    private lateinit var userInput: String
    private lateinit var type: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = OtpScreenLayoutBinding.inflate(layoutInflater)
        setContentView(binding.root)

//        viewModel = ViewModelProvider(this)[OtpViewModel::class.java]

        initView()
//        observeApi()
    }

    private fun initView() {

        userInput = intent.getStringExtra("user_input") ?: ""
        type = intent.getStringExtra("type") ?: ""

        binding.etPhone.setText(userInput)

        binding.backIv.setOnClickListener {
            finish()
        }

        binding.btnSendOtp.setOnClickListener {

            val mobile = binding.etPhone.text.toString().trim()

            if (mobile.length != 10) {
                binding.etPhone.error = "Enter valid mobile number"
                return@setOnClickListener
            }

//            viewModel.sendOtp(mobile)
        }

        binding.btnVerify.setOnClickListener {

            val otp = binding.etOtp.text.toString().trim()

            if (otp.length != 6) {
                binding.etOtp.error = "Enter valid OTP"
                return@setOnClickListener
            }

//            viewModel.verifyOtp(
//                binding.etPhone.text.toString().trim(),
//                otp
//            )
        }

        binding.txtResend.setOnClickListener {

//            viewModel.sendOtp(
//                binding.etPhone.text.toString().trim()
//            )
        }
    }

//    private fun observeApi() {
//
//        viewModel.sendOtpResponse.observe(this) {
//
//            if (it.success) {
//
//                Toast.makeText(this, it.message, Toast.LENGTH_SHORT).show()
//
//                binding.otpLayout.visibility = View.VISIBLE
//                binding.etPhone.isEnabled = false
//                binding.btnSendOtp.isEnabled = false
//
//                startTimer()
//
//            } else {
//
//                Toast.makeText(this, it.message, Toast.LENGTH_SHORT).show()
//            }
//        }
//
//        viewModel.verifyOtpResponse.observe(this) {
//
//            if (it.success) {
//
//                Toast.makeText(this, "Login Success", Toast.LENGTH_SHORT).show()
//
//                startActivity(
//                    Intent(this, WelcomeActivity::class.java)
//                )
//
//                finish()
//
//            } else {
//
//                Toast.makeText(this, "Invalid OTP", Toast.LENGTH_SHORT).show()
//            }
//        }
//    }

    private fun startTimer() {

        binding.txtResend.isEnabled = false

        countDownTimer =
            object : CountDownTimer(timerDuration, 1000) {

                override fun onTick(millisUntilFinished: Long) {

                    binding.txtTimer.text =
                        "Resend OTP in ${millisUntilFinished / 1000} sec"
                }

                override fun onFinish() {

                    binding.txtTimer.text = "You can resend OTP"

                    binding.txtResend.isEnabled = true
                }

            }.start()
    }

    override fun onDestroy() {
        super.onDestroy()
        if (::countDownTimer.isInitialized)
            countDownTimer.cancel()
    }
}