package com.android.mykoodugalapplication.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.android.mykoodugalapplication.R
import com.android.mykoodugalapplication.dataClass.SliderModel

class SliderAdapter(private val list: List<SliderModel>) :
    RecyclerView.Adapter<SliderAdapter.ViewHolder>() {

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

        holder.image.setImageResource(item.image)
        holder.text.text = item.text
    }

    override fun getItemCount(): Int {
        return list.size
    }
}