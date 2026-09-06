package com.android.mykoodugalapplication.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.android.mykoodugalapplication.dataClass.RecognitionItem
import com.android.mykoodugalapplication.databinding.ItemMediaBinding
import com.bumptech.glide.Glide

class MediaAdapter(
    private val list: List<RecognitionItem>,
    private val onItemClick: (RecognitionItem) -> Unit
) : RecyclerView.Adapter<MediaAdapter.ViewHolder>() {

    inner class ViewHolder(val binding: ItemMediaBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemMediaBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = list[position]
        Glide.with(holder.itemView.context)
            .load(list[position].icon)
            .into(holder.binding.mediaIv)

        holder.itemView.setOnClickListener {
            onItemClick(item)
        }
    }

    override fun getItemCount(): Int = list.size
}
