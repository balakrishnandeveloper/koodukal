package com.android.mykoodugalapplication.dataClass

import com.google.gson.annotations.SerializedName

data class DashboardResponse(

    @field:SerializedName("status")
    val status: String? = null,

    @field:SerializedName("message")
    val message: String? = null,

    @field:SerializedName("images")
    val images: List<BannerImage>? = null,

    @field:SerializedName("statistics")
    val statistics: Statistics? = null
)

data class BannerImage(

    @field:SerializedName("img_url")
    val imgUrl: String? = null,

    @field:SerializedName("text")
    val text: String? = null
)

data class Statistics(

    @field:SerializedName("location")
    val location: String? = null,

    @field:SerializedName("total_nests")
    val totalNests: Int? = null,

    @field:SerializedName("active_nests")
    val activeNests: Int? = null,

    @field:SerializedName("eggs")
    val eggs: Int? = null,

    @field:SerializedName("chicks")
    val chicks: Int? = null,

    @field:SerializedName("top_district")
    val topDistrict: String? = null
)
