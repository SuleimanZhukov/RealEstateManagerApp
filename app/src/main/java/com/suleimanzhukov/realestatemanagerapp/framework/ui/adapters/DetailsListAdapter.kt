package com.suleimanzhukov.realestatemanagerapp.framework.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.suleimanzhukov.realestatemanagerapp.databinding.DetailsInfoCardViewBinding
import com.suleimanzhukov.realestatemanagerapp.model.database.entities.PropertyEntity

class DetailsListAdapter() : RecyclerView.Adapter<DetailsListAdapter.PropertiesViewHolder>() {
    private lateinit var binding: DetailsInfoCardViewBinding
    private var propertyDetails: List<Int?> = mutableListOf()

    fun setDetails(details: PropertyEntity?) {
        propertyDetails = listOf(
            details?.beds,
            details?.baths,
            details?.garages,
            details?.area
        )
    }

    inner class PropertiesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(detail: Int, position: Int) = with(binding) {
            detailsInfoCardNumberTextView.text = detail.toString()
            detailsInfoCardParamTitle.text = when (position) {
                0 -> "Beds"
                1 -> "Baths"
                2 -> "Garages"
                3 -> "Area"
                else -> "None"
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PropertiesViewHolder {
        binding = DetailsInfoCardViewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PropertiesViewHolder(binding.root)
    }

    override fun onBindViewHolder(holder: PropertiesViewHolder, position: Int) {
        holder.bind(propertyDetails[position]!!, position)
    }

    override fun getItemCount(): Int {
        return propertyDetails.size
    }
}