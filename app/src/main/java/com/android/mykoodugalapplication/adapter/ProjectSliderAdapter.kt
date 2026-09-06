package com.android.mykoodugalapplication.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.android.mykoodugalapplication.R

class ProjectSliderAdapter(
    private val images: List<Int>
) : RecyclerView.Adapter<ProjectSliderAdapter.ViewHolder>() {

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val img: ImageView = view.findViewById(R.id.projectImage)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_project_slider, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount() = images.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.img.setImageResource(images[position])
    }
}
