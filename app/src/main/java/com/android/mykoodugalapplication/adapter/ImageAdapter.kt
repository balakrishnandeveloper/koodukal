package com.android.mykoodugalapplication.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.android.mykoodugalapplication.R
import com.android.mykoodugalapplication.dataClass.BirdImage

class ImageAdapter(private var list: List<BirdImage>) :
    RecyclerView.Adapter<ImageAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val img = itemView.findViewById<ImageView>(R.id.imgItem)
        val txtLocation = itemView.findViewById<TextView>(R.id.txtLocation)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_image, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.img.setImageResource(list[position].imageRes)
        holder.txtLocation.text = list[position].location.uppercase()
    }

    override fun getItemCount(): Int = list.size

    fun updateList(newList: List<BirdImage>) {
        list = newList
        notifyDataSetChanged()
    }
}