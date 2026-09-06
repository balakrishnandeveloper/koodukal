package com.android.mykoodugalapplication.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.android.mykoodugalapplication.dataClass.RecognitionItem
import com.android.mykoodugalapplication.databinding.ItemProjectBinding

class ProjectAdapter(
    private val list: List<RecognitionItem>,
    private val onItemClick: (RecognitionItem) -> Unit) : RecyclerView.Adapter<ProjectAdapter.ViewHolder>() {

    inner class ViewHolder(val binding: ItemProjectBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemProjectBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = list[position]
        holder.binding.projectIv.setImageResource(list[position].icon)

        holder.itemView.setOnClickListener {
            onItemClick(item)
        }
    }

    override fun getItemCount(): Int = list.size
}
