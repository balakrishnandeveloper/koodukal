package com.android.mykoodugalapplication.Repository

import com.android.mykoodugalapplication.commonUtils.BaseUrl
import com.android.mykoodugalapplication.commonUtils.NetworkManager
import com.android.mykoodugalapplication.commonUtils.awaitResult
import com.android.mykoodugalapplication.dataClass.DashboardResponse
import com.android.mykoodugalapplication.dataClass.LoginResponse
import com.android.mykoodugalapplication.dataClass.OtpResponse
import com.android.mykoodugalapplication.dataClass.RegisterResponse
import com.android.mykoodugalapplication.interfaceActs.NetworkApi
import com.android.mykoodugalapplication.interfaceActs.TaskCallback
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.toRequestBody
import javax.inject.Inject

class LoginRepository @Inject constructor() {

    private val completedJob = Job()
    private val backgroundScope = CoroutineScope(Dispatchers.IO + completedJob)
    private val foregroundScope = CoroutineScope(Dispatchers.Main)

    private val loginApi: NetworkApi by lazy {
        NetworkManager.baseURL(BaseUrl.baseUrl).serviceClass(NetworkApi::class.java).create()
    }

    fun getLoginResponse(mobileNumber: String, taskCallback: TaskCallback<LoginResponse>) {
        val mobileRequestBody = mobileNumber.toRequestBody("text/plain".toMediaTypeOrNull())
        backgroundScope.launch {
            when (val result = loginApi.getLogin(mobileRequestBody).awaitResult()) {
                is com.android.mykoodugalapplication.commonUtils.Result.Ok ->
                    foregroundScope.launch { taskCallback.onComplete(result.value) }
                is com.android.mykoodugalapplication.commonUtils.Result.Error ->
                    foregroundScope.launch { taskCallback.onException(result.exception) }
                is com.android.mykoodugalapplication.commonUtils.Result.Exception ->
                    foregroundScope.launch { taskCallback.onException(result.exception) }
                else -> {}
            }
        }
    }

    fun getRegisterResponse(
        name: String, email: String, mobileNumber: String, password: String,
        location: String, role: String, profileImage: MultipartBody.Part?,
        taskCallback: TaskCallback<RegisterResponse>
    ) {
        backgroundScope.launch {
            when (val result = loginApi.getRegister(
                name.toRequestBody("text/plain".toMediaTypeOrNull()),
                email.toRequestBody("text/plain".toMediaTypeOrNull()),
                mobileNumber.toRequestBody("text/plain".toMediaTypeOrNull()),
                password.toRequestBody("text/plain".toMediaTypeOrNull()),
                location.toRequestBody("text/plain".toMediaTypeOrNull()),
                role.toRequestBody("text/plain".toMediaTypeOrNull()),
                profileImage
            ).awaitResult()) {
                is com.android.mykoodugalapplication.commonUtils.Result.Ok ->
                    foregroundScope.launch { taskCallback.onComplete(result.value) }
                is com.android.mykoodugalapplication.commonUtils.Result.Error ->
                    foregroundScope.launch { taskCallback.onException(result.exception) }
                is com.android.mykoodugalapplication.commonUtils.Result.Exception ->
                    foregroundScope.launch { taskCallback.onException(result.exception) }
                else -> {}
            }
        }
    }

    fun getMobileResponse(mobileNumber: String, taskCallback: TaskCallback<RegisterResponse>) {
        val mobileRequestBody = mobileNumber.toRequestBody("text/plain".toMediaType())
        backgroundScope.launch {
            when (val result = loginApi.existingUserCheck(mobileRequestBody).awaitResult()) {
                is com.android.mykoodugalapplication.commonUtils.Result.Ok ->
                    foregroundScope.launch { taskCallback.onComplete(result.value) }
                is com.android.mykoodugalapplication.commonUtils.Result.Error ->
                    foregroundScope.launch { taskCallback.onException(result.exception) }
                is com.android.mykoodugalapplication.commonUtils.Result.Exception ->
                    foregroundScope.launch { taskCallback.onException(result.exception) }
                else -> {}
            }
        }
    }

    fun getDashboardResponse(userId: String, token: String, taskCallback: TaskCallback<DashboardResponse>) {
        backgroundScope.launch {
            when (val result = loginApi.getDashboard(
                userId.toRequestBody("text/plain".toMediaType()),
                token.toRequestBody("text/plain".toMediaType())
            ).awaitResult()) {
                is com.android.mykoodugalapplication.commonUtils.Result.Ok ->
                    foregroundScope.launch { taskCallback.onComplete(result.value) }
                is com.android.mykoodugalapplication.commonUtils.Result.Error ->
                    foregroundScope.launch { taskCallback.onException(result.exception) }
                is com.android.mykoodugalapplication.commonUtils.Result.Exception ->
                    foregroundScope.launch { taskCallback.onException(result.exception) }
                else -> {}
            }
        }
    }

    fun getGenerateOtpResponse(mobileNumber: String, taskCallback: TaskCallback<OtpResponse>) {
        val mobileRequestBody = mobileNumber.toRequestBody("text/plain".toMediaTypeOrNull())
        backgroundScope.launch {
            when (val result = loginApi.generateOtp(mobileRequestBody).awaitResult()) {
                is com.android.mykoodugalapplication.commonUtils.Result.Ok ->
                    foregroundScope.launch { taskCallback.onComplete(result.value) }
                is com.android.mykoodugalapplication.commonUtils.Result.Error ->
                    foregroundScope.launch { taskCallback.onException(result.exception) }
                is com.android.mykoodugalapplication.commonUtils.Result.Exception ->
                    foregroundScope.launch { taskCallback.onException(result.exception) }
                else -> {}
            }
        }
    }

    fun getValidateOtpResponse(mobileNumber: String, otp: String, taskCallback: TaskCallback<OtpResponse>) {
        backgroundScope.launch {
            when (val result = loginApi.validateOtp(
                mobileNumber.toRequestBody("text/plain".toMediaTypeOrNull()),
                otp.toRequestBody("text/plain".toMediaTypeOrNull())
            ).awaitResult()) {
                is com.android.mykoodugalapplication.commonUtils.Result.Ok ->
                    foregroundScope.launch { taskCallback.onComplete(result.value) }
                is com.android.mykoodugalapplication.commonUtils.Result.Error ->
                    foregroundScope.launch { taskCallback.onException(result.exception) }
                is com.android.mykoodugalapplication.commonUtils.Result.Exception ->
                    foregroundScope.launch { taskCallback.onException(result.exception) }
                else -> {}
            }
        }
    }
}
