package com.suleimanzhukov.realestatemanagerapp.framework.ui.main

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import coil.load
import com.suleimanzhukov.realestatemanagerapp.R
import com.suleimanzhukov.realestatemanagerapp.databinding.FragmentMainBinding
import com.suleimanzhukov.realestatemanagerapp.di.DaggerRealEstateComponent
import com.suleimanzhukov.realestatemanagerapp.framework.ui.adapters.PropertiesListAdapter
import com.suleimanzhukov.realestatemanagerapp.framework.ui.auth.AuthViewModel
import com.suleimanzhukov.realestatemanagerapp.framework.ui.auth.SignUpFragment
import com.suleimanzhukov.realestatemanagerapp.model.database.entities.AgentEntity
import com.suleimanzhukov.realestatemanagerapp.model.database.entities.PropertyEntity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import javax.inject.Inject

class MainFragment : Fragment() {

    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var viewModel: AuthViewModel

    private lateinit var navController: NavController

    private var agent: AgentEntity? = null
    private var email: String? = null
    private var properties: List<PropertyEntity?> = mutableListOf()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        DaggerRealEstateComponent.builder().build().getForMainFragment(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)

        subscribeToLiveData()
        isLoggedIn()
        buttonsInit()

        setProperties()
    }

    private fun setProperties() {
        CoroutineScope(Main).launch {
            val job = async(IO) {
                properties = viewModel.getAllProperties(requireContext())
            }
            job.await()
            setAdapter()
        }
    }

    private fun setAdapter() = with(binding) {
        val propertiesAdapter = PropertiesListAdapter(object : OnAdapterItemClickListener {
            override fun onItemClick(property: PropertyEntity, position: Int) {
                val bundle = Bundle().apply {
                    putString("receiver", property.id.toString())
                    Log.d("TAG", "onItemClick: ${property.id.toString()}")
                }
                navController.navigate(R.id.action_mainFragment_to_detailsFragment, bundle)
            }
        })
        propertiesAdapter.setProperties(properties)
        mainRecyclerView.adapter = propertiesAdapter
        mainRecyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
    }

    private fun isLoggedIn() = with(binding) {
        email = getEmail()
        if (email == null || email == "") {
            Log.d("TAG", "onViewCreated: Email: $email")
            authImg.load(R.drawable.account_circle_icon)
            authButton.setOnClickListener {
                navController.navigate(R.id.action_mainFragment_to_authFragment)
            }
        } else {
            CoroutineScope(Main).launch {
                val job = async(IO) {
                    viewModel.getAgentByEmail(email!!, requireContext())
                }
                job.await()
                Log.d("TAG", "onViewCreated: IN ASYNC(IO) Email: $email, Password: ${agent?.password}," +
                        "Username: ${agent?.username}, Image: ${agent?.profileImg}")
                val uri = Uri.parse(agent?.profileImg)
                authImg.load(uri)
                authButton.setOnClickListener {
                    navController.navigate(R.id.action_mainFragment_to_accountAgentFragment)
                }
            }
        }
    }

    private fun getEmail(): String? {
        val preferencesEditor = activity?.getSharedPreferences(SignUpFragment.SHARED_TAG, Context.MODE_PRIVATE)
        return preferencesEditor?.getString(SignUpFragment.EMAIL_TAG, "")
    }

    private fun buttonsInit() = with(binding) {
        settingsButton.setOnClickListener {
            navController.navigate(R.id.action_mainFragment_to_settingsFragment)
        }
        searchButton.setOnClickListener {
            navController.navigate(R.id.action_mainFragment_to_agentsListFragment)
        }
    }

    private fun subscribeToLiveData() {
        viewModel.getAgentLiveData().observe(viewLifecycleOwner, Observer {
            agent = viewModel.getAgentLiveData().value
        })
        viewModel.getPropertiesLiveData().observe(viewLifecycleOwner, Observer {
            properties = viewModel.getPropertiesLiveData().value!!
        })
    }

    interface OnAdapterItemClickListener {
        fun onItemClick(property: PropertyEntity, position: Int)
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    companion object {
        fun newInstance() = MainFragment()
    }
}