package com.suleimanzhukov.realestatemanagerapp.framework.ui.adapters

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import coil.load
import com.smarteist.autoimageslider.SliderViewAdapter
import com.suleimanzhukov.realestatemanagerapp.databinding.SliderImageLayoutBinding
import com.suleimanzhukov.realestatemanagerapp.framework.ui.details.DetailsFragment
import com.suleimanzhukov.realestatemanagerapp.framework.ui.main.MainFragment
import com.suleimanzhukov.realestatemanagerapp.model.database.entities.PictureEntity

class DetailsPicturesAdapter(
    private val context: Context,
    private val pictures: List<PictureEntity>
) : SliderViewAdapter<DetailsPicturesAdapter.DetailsPicturesViewHolder>() {

    private lateinit var binding: SliderImageLayoutBinding

    inner class DetailsPicturesViewHolder(itemView: View) : SliderViewAdapter.ViewHolder(itemView) {
        fun bind(position: Int) = with(binding) {
            pictures[position].orderNumber = position
            sliderImage.load(pictures[position].url)
            Log.d("TAG", "bind: ${pictures[position].url}")
        }
    }

    override fun getCount(): Int {
        return pictures.size
    }

    override fun onCreateViewHolder(parent: ViewGroup?): DetailsPicturesViewHolder {
        binding = SliderImageLayoutBinding.inflate(LayoutInflater.from(context), parent, false)
        return DetailsPicturesViewHolder(binding.root)
    }

    override fun onBindViewHolder(viewHolder: DetailsPicturesViewHolder, position: Int) {
        viewHolder.bind(position)
    }
}