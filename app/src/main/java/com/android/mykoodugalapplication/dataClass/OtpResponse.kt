package com.android.mykoodugalapplication.dataClass

import com.google.gson.annotations.SerializedName

data class OtpResponse(

    @field:SerializedName("status")
    val status: String? = null,

    @field:SerializedName("message")
    val message: String? = null
)
