package com.android.mykoodugalapplication.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.android.mykoodugalapplication.databinding.ActivityLoginBinding
import com.android.mykoodugalapplication.viwemodel.LoginViewModel

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding

    private val viewModel by lazy {
        ViewModelProvider(this)[LoginViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        intiView()
        observer()
    }

    private fun  intiView(){

        binding.btnLogin.setOnClickListener {

            val input = binding.etEmail.text.toString().trim()
            val password = binding.etPassword.text.toString().trim()

            // Email / Mobile validation
            if (input.isEmpty()) {
                binding.etEmail.error = "Enter Mobile Number"
                return@setOnClickListener
            }


            // Email or mobile validation
            if (isValidEmail(input) || isValidMobile(input)) {
                binding.progressBar.visibility = View.VISIBLE
                viewModel.loginRequest(input)

            } else {
                binding.etEmail.error = "Enter Mobile Number"
            }
        }

        binding.tvRegister.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }

        binding.tvForgotPassword.setOnClickListener {
            val intent = Intent(this, ForgotPasswordActivity::class.java)
            startActivity(intent)
        }
    }

    private fun isValidEmail(email: String): Boolean {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    private fun isValidMobile(mobile: String): Boolean {
        return mobile.length == 10 && mobile.all { it.isDigit() }
    }

    private fun observer() {
        viewModel.loginDetailResponse.observe(this) {
            binding.progressBar.visibility = View.GONE
            if (it != null) {
                val message = it.message
                val status = it.status.toString()
                if (status == "1") {
                    val intent = Intent(this, OtpActivity::class.java)
                    intent.putExtra("user_input", binding.etEmail.text.toString().trim())
                    intent.putExtra("type","Login")
                    startActivity(intent)
                    finish()
                    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
                }else {
                    Toast.makeText(applicationContext, it.message, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}