package com.android.mykoodugalapplication.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.android.mykoodugalapplication.databinding.ActivityForgotPasswordBinding

class ForgotPasswordActivity : AppCompatActivity() {
    private lateinit var binding: ActivityForgotPasswordBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityForgotPasswordBinding.inflate(layoutInflater)
        setContentView(binding.root)

        intiView()
    }

    private fun  intiView(){
        binding.btnpassword.setOnClickListener {

            val oldPassword = binding.etPassword.text.toString().trim()
            val newPassword = binding.etNewpwd.text.toString().trim()
            val confirmPassword = binding.etConfirmpwd.text.toString().trim()

            if (oldPassword.isEmpty()) {
                binding.etPassword.error = "Enter old password"
                return@setOnClickListener
            }

            if (newPassword.isEmpty()) {
                binding.etNewpwd.error = "Enter new password"
                return@setOnClickListener
            }

            if (confirmPassword.isEmpty()) {
                binding.etConfirmpwd.error = "Enter confirm password"
                return@setOnClickListener
            }

            if (newPassword.length < 6) {
                binding.etNewpwd.error = "Password must be at least 6 characters"
                return@setOnClickListener
            }

            if (newPassword != confirmPassword) {
                binding.etConfirmpwd.error = "Passwords do not match"
                return@setOnClickListener
            }

            // (Optional) Check old password with saved password
            val savedPassword = "123456" // replace with real stored value

            if (oldPassword != savedPassword) {
                binding.etPassword.error = "Old password is incorrect"
                return@setOnClickListener
            }

            // ✅ Success
            Toast.makeText(this, "Password changed successfully", Toast.LENGTH_SHORT).show()
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}