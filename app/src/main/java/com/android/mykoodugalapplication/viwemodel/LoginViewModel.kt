package com.android.mykoodugalapplication.viwemodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.android.mykoodugalapplication.Repository.LoginRepository
import com.android.mykoodugalapplication.dataClass.DashboardResponse
import com.android.mykoodugalapplication.dataClass.LoginResponse
import com.android.mykoodugalapplication.dataClass.OtpResponse
import com.android.mykoodugalapplication.dataClass.RegisterResponse
import com.android.mykoodugalapplication.interfaceActs.TaskCallback
import okhttp3.MultipartBody

class LoginViewModel : ViewModel() {

    // Login
    var loginDetails = MutableLiveData<LoginResponse>()
    val loginDetailResponse: LiveData<LoginResponse> get() = loginDetails

    // Register
    var registerDetails = MutableLiveData<RegisterResponse>()
    val registerDetailsResponse: LiveData<RegisterResponse> get() = registerDetails

    // Mobile check
    var mobileDetails = MutableLiveData<RegisterResponse>()
    val mobileDetailsResponse: LiveData<RegisterResponse> get() = mobileDetails

    // Dashboard
    var dashboardDetails = MutableLiveData<DashboardResponse>()
    val dashDetailsResponse: LiveData<DashboardResponse> get() = dashboardDetails

    // Generate OTP
    var generateOtpDetails = MutableLiveData<OtpResponse>()
    val generateOtpResponse: LiveData<OtpResponse> get() = generateOtpDetails

    // Validate OTP
    var validateOtpDetails = MutableLiveData<OtpResponse>()
    val validateOtpResponse: LiveData<OtpResponse> get() = validateOtpDetails

    private val repository: LoginRepository by lazy { LoginRepository() }

    fun loginRequest(mobileNumber: String) {
        repository.getLoginResponse(mobileNumber, object : TaskCallback<LoginResponse> {
            override fun onException(t: Throwable?) { println("---process--- Exception $t") }
            override fun onComplete(result: LoginResponse?) { loginDetails.postValue(result!!) }
        })
    }

    fun registerRequest(
        name: String, email: String, mobileNumber: String, password: String,
        location: String, role: String, profileImage: MultipartBody.Part?
    ) {
        repository.getRegisterResponse(name, email, mobileNumber, password, location, role, profileImage,
            object : TaskCallback<RegisterResponse> {
                override fun onException(t: Throwable?) { println("---process--- Exception $t") }
                override fun onComplete(result: RegisterResponse?) { registerDetails.postValue(result!!) }
            })
    }

    fun verifyMobile(mobileNumber: String) {
        repository.getMobileResponse(mobileNumber, object : TaskCallback<RegisterResponse> {
            override fun onException(t: Throwable?) { println("---process--- Exception $t") }
            override fun onComplete(result: RegisterResponse?) { mobileDetails.postValue(result!!) }
        })
    }

    fun getDashboard(userId: String, token: String) {
        repository.getDashboardResponse(userId, token, object : TaskCallback<DashboardResponse> {
            override fun onException(t: Throwable?) { println("---process--- Exception $t") }
            override fun onComplete(result: DashboardResponse?) { dashboardDetails.postValue(result!!) }
        })
    }

    fun generateOtp(mobileNumber: String) {
        repository.getGenerateOtpResponse(mobileNumber, object : TaskCallback<OtpResponse> {
            override fun onException(t: Throwable?) { println("---process--- Exception $t") }
            override fun onComplete(result: OtpResponse?) { generateOtpDetails.postValue(result!!) }
        })
    }

    fun validateOtp(mobileNumber: String, otp: String) {
        repository.getValidateOtpResponse(mobileNumber, otp, object : TaskCallback<OtpResponse> {
            override fun onException(t: Throwable?) { println("---process--- Exception $t") }
            override fun onComplete(result: OtpResponse?) { validateOtpDetails.postValue(result!!) }
        })
    }
}
