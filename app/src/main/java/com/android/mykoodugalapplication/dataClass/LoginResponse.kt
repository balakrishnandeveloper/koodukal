package com.android.mykoodugalapplication.dataClass

import com.google.gson.annotations.SerializedName

data class LoginResponse(

    @field:SerializedName("status")
    val status: String? = null,

    @field:SerializedName("message")
    val message: String? = null,

    @field:SerializedName("token")
    val token: String? = null
)
