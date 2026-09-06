package com.android.mykoodugalapplication.viwemodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.android.mykoodugalapplication.Repository.LoginRepository
import com.android.mykoodugalapplication.dataClass.DashboardResponse
import com.android.mykoodugalapplication.dataClass.LoginResponse
import com.android.mykoodugalapplication.dataClass.RegisterResponse
import com.android.mykoodugalapplication.interfaceActs.TaskCallback
import okhttp3.MultipartBody

class LoginViewModel : ViewModel() {

    //login

    var loginDetails = MutableLiveData<LoginResponse>()
    val loginDetailResponse: LiveData<LoginResponse> get() = loginDetails


    //register
    var registerDetails = MutableLiveData<RegisterResponse>()
    val registerDetailsResponse: LiveData<RegisterResponse> get() = registerDetails



    var mobileDetails = MutableLiveData<RegisterResponse>()
    val mobileDetailsResponse: LiveData<RegisterResponse> get() = mobileDetails



    var dashboardDetails = MutableLiveData<DashboardResponse>()
    val dashDetailsResponse: LiveData<DashboardResponse> get() = dashboardDetails

    private val repository: LoginRepository by lazy {
        LoginRepository()
    }




    fun loginRequest(mobileNumber:String) {
        repository.getLoginResponse(mobileNumber ,object : TaskCallback<LoginResponse> {
            override fun onException(t: Throwable?) {
                println("---process--- Calling Exception $t")
            }

            override fun onComplete(result: LoginResponse?) {
                loginDetails.postValue(result!!)
            }
        })
    }




    fun registerRequest(name:String,email:String,mobileNumber:String,password:String,location:String,role:String,profileImage: MultipartBody.Part?) {
        repository.getRegisterResponse(name,email,mobileNumber,password,location,role,profileImage,object : TaskCallback<RegisterResponse> {
            override fun onException(t: Throwable?) {
                println("---process--- Calling Exception $t")
            }

            override fun onComplete(result: RegisterResponse?) {
                registerDetails.postValue(result!!)
            }
        })
    }

    fun verifyMobile(mobileNumber:String) {
        repository.getMobileResponse(mobileNumber ,object : TaskCallback<RegisterResponse> {
            override fun onException(t: Throwable?) {
                println("---process--- Calling Exception $t")
            }

            override fun onComplete(result: RegisterResponse?) {
                mobileDetails.postValue(result!!)
            }
        })
    }


    fun getDashboard(userId: String, token: String) {

        repository.getDashboardResponse(userId ,token,object : TaskCallback<DashboardResponse> {
            override fun onException(t: Throwable?) {
                println("---process--- Calling Exception $t")
            }

            override fun onComplete(result: DashboardResponse?) {
                dashboardDetails.postValue(result!!)
            }
        })
    }

}