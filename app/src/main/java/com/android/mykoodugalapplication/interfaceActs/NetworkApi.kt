package com.android.mykoodugalapplication.interfaceActs

import com.android.mykoodugalapplication.dataClass.DashboardResponse
import com.android.mykoodugalapplication.dataClass.LoginResponse
import com.android.mykoodugalapplication.dataClass.OtpResponse
import com.android.mykoodugalapplication.dataClass.RegisterResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface NetworkApi {

    @Multipart
    @POST("app-login/")
    fun getLogin(
        @Part("mobile_number") mobile: RequestBody
    ): Call<LoginResponse>

    @Multipart
    @POST("app-register/")
    fun getRegister(
        @Part("name") name: RequestBody,
        @Part("email") email: RequestBody,
        @Part("mobile_number") mobile: RequestBody,
        @Part("Password") password: RequestBody,
        @Part("location") location: RequestBody,
        @Part("role") role: RequestBody,
        @Part profile_image: MultipartBody.Part?
    ): Call<RegisterResponse>

    @Multipart
    @POST("app-existing_user_check")
    fun existingUserCheck(
        @Part("mobile_number") mobileNumber: RequestBody
    ): Call<RegisterResponse>

    @Multipart
    @POST("app-dashboard")
    fun getDashboard(
        @Part("user_id") userId: RequestBody,
        @Part("token") token: RequestBody
    ): Call<DashboardResponse>

    @Multipart
    @POST("app-generate_otp")
    fun generateOtp(
        @Part("Mobile_number") mobileNumber: RequestBody
    ): Call<OtpResponse>

    @Multipart
    @POST("app-validate_otp")
    fun validateOtp(
        @Part("Mobile_number") mobileNumber: RequestBody,
        @Part("Otp") otp: RequestBody
    ): Call<OtpResponse>
}
