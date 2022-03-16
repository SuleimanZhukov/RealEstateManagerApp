package com.suleimanzhukov.realestatemanagerapp.framework.ui.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import coil.load
import com.smarteist.autoimageslider.SliderViewAdapter
import com.suleimanzhukov.realestatemanagerapp.databinding.SliderImageLayoutBinding
import com.suleimanzhukov.realestatemanagerapp.model.database.entities.PictureEntity

class DetailsSliderAdapter(
    private val context: Context,
    private val pictures: List<PictureEntity>
) : SliderViewAdapter<DetailsSliderAdapter.DetailsSliderViewHolder>() {

    private lateinit var binding: SliderImageLayoutBinding

    inner class DetailsSliderViewHolder(itemView: View) : SliderViewAdapter.ViewHolder(itemView) {
        fun bind(position: Int) = with(binding) {
            sliderImage.load(pictures[position].url)
        }
    }

    override fun getCount(): Int {
        return pictures.size
    }

    override fun onCreateViewHolder(parent: ViewGroup?): DetailsSliderViewHolder {
        binding = SliderImageLayoutBinding.inflate(LayoutInflater.from(context), parent, false)
        return DetailsSliderViewHolder(binding.root)
    }

    override fun onBindViewHolder(viewHolder: DetailsSliderViewHolder, position: Int) {
        viewHolder.bind(position)
    }
}