package com.suleimanzhukov.realestatemanagerapp.framework.ui.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.suleimanzhukov.realestatemanagerapp.databinding.PublishPicturesViewBinding
import com.suleimanzhukov.realestatemanagerapp.model.database.entities.PictureEntity

class PublishPicturesAdapter(
    private val context: Context,

) : RecyclerView.Adapter<PublishPicturesAdapter.PublishPicturesViewHolder>() {

    private val pictures = mutableListOf<PictureEntity>()
    private lateinit var binding: PublishPicturesViewBinding

    @SuppressLint("NotifyDataSetChanged")
    fun addPublishPictures(picture: PictureEntity) {
        pictures.add(picture)
        notifyDataSetChanged()
    }

    inner class PublishPicturesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(position: Int) = with(binding) {
            publishImageView.load(pictures[position].url)
            val num = position + 1
            publishImageViewNumber.text = "${num}"
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PublishPicturesViewHolder {
        binding = PublishPicturesViewBinding.inflate(LayoutInflater.from(context), parent, false)
        return PublishPicturesViewHolder(binding.root)
    }

    override fun onBindViewHolder(holder: PublishPicturesViewHolder, position: Int) {
        holder.bind(position)
    }

    override fun getItemCount(): Int {
        return pictures.size
    }
}