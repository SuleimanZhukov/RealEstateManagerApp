package com.suleimanzhukov.realestatemanagerapp.framework.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.suleimanzhukov.realestatemanagerapp.databinding.MainCardViewBinding
import com.suleimanzhukov.realestatemanagerapp.model.database.entities.AgentEntity
import com.suleimanzhukov.realestatemanagerapp.model.database.entities.PropertyEntity

class PropertiesListAdapter() : RecyclerView.Adapter<PropertiesListAdapter.PropertiesViewHolder>() {
    private lateinit var binding: MainCardViewBinding
    private var properties: List<PropertyEntity?> = mutableListOf()

    fun setProperties(tempProperties: List<PropertyEntity?>) {
        properties = tempProperties
    }

    inner class PropertiesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(property: PropertyEntity) = with(binding) {

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PropertiesViewHolder {
        binding = MainCardViewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PropertiesViewHolder(binding.root)
    }

    override fun onBindViewHolder(holder: PropertiesViewHolder, position: Int) {
        holder.bind(properties[position]!!)
    }

    override fun getItemCount(): Int {
        return properties.size
    }
}