package com.suleimanzhukov.realestatemanagerapp.framework.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.suleimanzhukov.realestatemanagerapp.databinding.DetailsOtherPropertiesCardViewBinding
import com.suleimanzhukov.realestatemanagerapp.databinding.MainCardViewBinding
import com.suleimanzhukov.realestatemanagerapp.framework.ui.main.MainFragment
import com.suleimanzhukov.realestatemanagerapp.model.database.entities.PropertyEntity

class OtherPropertiesAdapter(
    private val onItemClick: MainFragment.OnAdapterItemClickListener
) : RecyclerView.Adapter<OtherPropertiesAdapter.OtherPropertiesViewHolder>() {
    private lateinit var binding: DetailsOtherPropertiesCardViewBinding
    private var properties: List<PropertyEntity?> = mutableListOf()

    fun setOtherProperties(tempProperties: List<PropertyEntity?>) {
        properties = tempProperties
    }

    inner class OtherPropertiesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(property: PropertyEntity, position: Int) = with(binding) {
            val pricing = "$${property.price}"
            detailsOtherPriceCard.text = pricing
            val text = "${property.beds} beds / ${property.area} m²"
            detailsOtherSquareCard.text = text

            root.setOnClickListener { onItemClick.onItemClick(property, position) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OtherPropertiesViewHolder {
        binding = DetailsOtherPropertiesCardViewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return OtherPropertiesViewHolder(binding.root)
    }

    override fun onBindViewHolder(holder: OtherPropertiesViewHolder, position: Int) {
        holder.bind(properties[position]!!, position)
    }

    override fun getItemCount(): Int {
        return properties.size
    }
}