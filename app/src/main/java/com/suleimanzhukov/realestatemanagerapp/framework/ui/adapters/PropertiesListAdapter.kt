package com.suleimanzhukov.realestatemanagerapp.framework.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.suleimanzhukov.realestatemanagerapp.databinding.MainCardViewBinding
import com.suleimanzhukov.realestatemanagerapp.framework.ui.auth.AuthViewModel
import com.suleimanzhukov.realestatemanagerapp.framework.ui.main.MainFragment
import com.suleimanzhukov.realestatemanagerapp.framework.ui.main.MainViewModel
import com.suleimanzhukov.realestatemanagerapp.model.database.entities.AgentEntity
import com.suleimanzhukov.realestatemanagerapp.model.database.entities.PictureEntity
import com.suleimanzhukov.realestatemanagerapp.model.database.entities.PropertyEntity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class PropertiesListAdapter(
    private val onItemClick: MainFragment.OnAdapterItemClickListener,
    private val viewModel: AuthViewModel
) : RecyclerView.Adapter<PropertiesListAdapter.PropertiesViewHolder>() {
    private lateinit var binding: MainCardViewBinding
    private var properties: List<PropertyEntity?> = mutableListOf()

    fun setProperties(tempProperties: List<PropertyEntity?>) {
        properties = tempProperties
    }

    inner class PropertiesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(property: PropertyEntity, position: Int) = with(binding) {
            CoroutineScope(Main).launch {
                lateinit var picture: PictureEntity
                val job = async(IO) {
                    picture = viewModel.getAllPicturesForPropertyId(property.id)[0]
                }
                job.await()
                mainCardImageView.load(picture.url)
                mainCardPriceTextView.text = "$${property.price}"
                mainCardAddressTextView.text = property.address
                val text = "${property.beds} bedrooms / ${property.baths} bathrooms / ${property.area} m²"
                mainCardParamsTextView.text = text
                root.setOnClickListener { onItemClick.onItemClick(property, position) }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PropertiesViewHolder {
        binding = MainCardViewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PropertiesViewHolder(binding.root)
    }

    override fun onBindViewHolder(holder: PropertiesViewHolder, position: Int) {
        holder.bind(properties[position]!!, position)
    }

    override fun getItemCount(): Int {
        return properties.size
    }
}