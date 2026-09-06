package com.android.mykoodugalapplication.dataClass

data class DashboardResponse(
    val status: String,
    val message: String,
    val images: List<BannerImage>,
    val statistics: Statistics
)

data class BannerImage(
    val img_url: String,
    val text: String
)

data class Statistics(
    val location: String,
    val total_nests: Int,
    val active_nests: Int,
    val eggs: Int,
    val chicks: Int,
    val top_district: String
)
