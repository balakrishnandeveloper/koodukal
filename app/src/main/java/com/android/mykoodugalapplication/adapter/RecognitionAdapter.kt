package com.android.mykoodugalapplication.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.android.mykoodugalapplication.dataClass.RecognitionItem
import com.android.mykoodugalapplication.databinding.GridItemBinding

class RecognitionAdapter(
    private val list: List<RecognitionItem>,
    private val onItemClick: (RecognitionItem) -> Unit) : RecyclerView.Adapter<RecognitionAdapter.ViewHolder>() {

    inner class ViewHolder(val binding: GridItemBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = GridItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = list[position]
        holder.binding.titleTxt.text = item.title
        holder.binding.projectIv.setImageResource(item.icon)
        holder.itemView.setOnClickListener {
            onItemClick(item)
        }
    }

    override fun getItemCount(): Int = list.size
}
