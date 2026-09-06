package com.android.mykoodugalapplication.activity

import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.android.mykoodugalapplication.databinding.OtpScreenLayoutBinding
import com.android.mykoodugalapplication.viwemodel.LoginViewModel

class OtpActivity : AppCompatActivity() {

    private lateinit var binding: OtpScreenLayoutBinding

    private val viewModel by lazy {
        ViewModelProvider(this)[LoginViewModel::class.java]
    }

    private val timerDuration = 60000L
    private lateinit var countDownTimer: CountDownTimer
    private lateinit var userInput: String
    private lateinit var type: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = OtpScreenLayoutBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initView()
        observeApi()
    }

    private fun initView() {
        userInput = intent.getStringExtra("user_input") ?: ""
        type = intent.getStringExtra("type") ?: ""

        binding.etPhone.setText(userInput)

        binding.backIv.setOnClickListener { finish() }

        binding.btnSendOtp.setOnClickListener {
            val mobile = binding.etPhone.text.toString().trim()
            if (mobile.length != 10) {
                binding.etPhone.error = "Enter valid mobile number"
                return@setOnClickListener
            }
            binding.btnSendOtp.isEnabled = false
            viewModel.generateOtp(mobile)
        }

        binding.btnVerify.setOnClickListener {
            val otp = binding.etOtp.text.toString().trim()
            if (otp.length != 6) {
                binding.etOtp.error = "Enter valid OTP"
                return@setOnClickListener
            }
            binding.btnVerify.isEnabled = false
            viewModel.validateOtp(binding.etPhone.text.toString().trim(), otp)
        }

        binding.txtResend.setOnClickListener {
            binding.txtResend.isEnabled = false
            viewModel.generateOtp(binding.etPhone.text.toString().trim())
        }
    }

    private fun observeApi() {

        viewModel.generateOtpResponse.observe(this) {
            binding.btnSendOtp.isEnabled = true
            if (it != null) {
                Toast.makeText(this, it.message, Toast.LENGTH_SHORT).show()
                if (it.status == "1") {
                    binding.otpLayout.visibility = View.VISIBLE
                    binding.etPhone.isEnabled = false
                    binding.btnSendOtp.isEnabled = false
                    startTimer()
                }
            }
        }

        viewModel.validateOtpResponse.observe(this) {
            binding.btnVerify.isEnabled = true
            if (it != null) {
                Toast.makeText(this, it.message, Toast.LENGTH_SHORT).show()
                if (it.status == "1") {
                    // OTP validated — go to WelcomeActivity and clear back stack
                    val intent = Intent(this, WelcomeActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(intent)
                    finish()
                }
            }
        }
    }

    private fun startTimer() {
        binding.txtResend.isEnabled = false
        countDownTimer = object : CountDownTimer(timerDuration, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                binding.txtTimer.text = "Resend OTP in ${millisUntilFinished / 1000} sec"
            }
            override fun onFinish() {
                binding.txtTimer.text = "You can resend OTP"
                binding.txtResend.isEnabled = true
            }
        }.start()
    }

    override fun onDestroy() {
        super.onDestroy()
        if (::countDownTimer.isInitialized) countDownTimer.cancel()
    }
}
