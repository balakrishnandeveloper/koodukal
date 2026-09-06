package com.android.mykoodugalapplication.adapter

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.android.mykoodugalapplication.activity.ImagePreviewActivity
import com.android.mykoodugalapplication.databinding.ItemUploadImagBinding
import com.bumptech.glide.Glide

class ImageUploadAdapter(
    private val context: Context,
    private val imageList: ArrayList<String>,
    private val onDeleteClick: (Int) -> Unit
) : RecyclerView.Adapter<ImageUploadAdapter.ImageViewHolder>() {

    inner class ImageViewHolder(val binding: ItemUploadImagBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {

        val binding = ItemUploadImagBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )

        return ImageViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return imageList.size
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {

        val imageUri = imageList[position]

        Glide.with(context)
            .load(Uri.parse(imageUri))
            .into(holder.binding.ivImage)

        // Preview Image
        holder.binding.ivImage.setOnClickListener {

            val intent = Intent(context, ImagePreviewActivity::class.java)
            intent.putExtra("image", imageUri)
            context.startActivity(intent)
        }

        // Delete Image
        holder.binding.ivDelete.setOnClickListener {

            val pos = holder.adapterPosition

            if (pos != RecyclerView.NO_POSITION) {
                onDeleteClick(pos)
            }
        }
    }
}