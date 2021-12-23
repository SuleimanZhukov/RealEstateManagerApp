package com.suleimanzhukov.realestatemanagerapp.framework.ui.agentslist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.suleimanzhukov.realestatemanagerapp.databinding.FragmentAgentsListBinding
import com.suleimanzhukov.realestatemanagerapp.framework.ui.adapter.AgentListAdapter

class AgentsListFragment : Fragment() {

    private var _binding: FragmentAgentsListBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentAgentsListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val agentListAdapter = AgentListAdapter()
        agentListAdapter.setAgents()

        binding.agentsListRecyclerView.adapter = agentListAdapter
        binding.agentsListRecyclerView.layoutManager =LinearLayoutManager(context)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        fun newInstance() = AgentsListFragment()
    }
}