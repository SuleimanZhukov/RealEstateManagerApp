package com.suleimanzhukov.realestatemanagerapp.framework.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.suleimanzhukov.realestatemanagerapp.databinding.AgentListItemBinding
import com.suleimanzhukov.realestatemanagerapp.model.database.entities.AgentEntity

class AgentListAdapter() : RecyclerView.Adapter<AgentListAdapter.AgentsViewHolder>() {
    private lateinit var binding: AgentListItemBinding
    private var agents: List<AgentEntity> = mutableListOf()

    fun setAgents(tempAgents: List<AgentEntity>) {
        agents = tempAgents
    }

    inner class AgentsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(agent: AgentEntity) = with(binding) {
            agentListItemTextView.text = agent.username
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AgentsViewHolder {
        binding = AgentListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return AgentsViewHolder(binding.root)
    }

    override fun onBindViewHolder(holder: AgentsViewHolder, position: Int) {
        holder.bind(agents[position])
    }

    override fun getItemCount(): Int {
        return agents.size
    }
}