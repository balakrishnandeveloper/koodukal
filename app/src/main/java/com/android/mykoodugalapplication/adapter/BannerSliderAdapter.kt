package com.android.mykoodugalapplication.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.android.mykoodugalapplication.R
import com.android.mykoodugalapplication.commonUtils.BaseUrl
import com.android.mykoodugalapplication.dataClass.BannerImage
import com.bumptech.glide.Glide

class BannerSliderAdapter(private val list: List<BannerImage>) :
    RecyclerView.Adapter<BannerSliderAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val image: ImageView = view.findViewById(R.id.imgSlider)
        val text: TextView = view.findViewById(R.id.txtSliderText)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.slider_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = list[position]
        // img_url is a relative path e.g. "/static/banner_images/img.jpg"
        val fullUrl = BaseUrl.baseUrl.trimEnd('/') + (item.imgUrl ?: "")
        Glide.with(holder.itemView.context)
            .load(fullUrl)
            .placeholder(R.drawable.sparrow1)
            .error(R.drawable.sparrow1)
            .centerCrop()
            .into(holder.image)
        holder.text.text = item.text ?: ""
    }

    override fun getItemCount(): Int = list.size
}
