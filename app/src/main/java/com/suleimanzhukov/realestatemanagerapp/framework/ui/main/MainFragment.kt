package com.suleimanzhukov.realestatemanagerapp.framework.ui.main

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import coil.load
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.storage.FirebaseStorage
import com.suleimanzhukov.realestatemanagerapp.R
import com.suleimanzhukov.realestatemanagerapp.RealEstateApplication
import com.suleimanzhukov.realestatemanagerapp.databinding.FragmentMainBinding
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
import kotlin.io.path.createTempDirectory

class MainFragment : Fragment() {

    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var viewModel: AuthViewModel

    private lateinit var navController: NavController

    private lateinit var auth: FirebaseAuth

    private var agent: AgentEntity? = null
    private var email: String? = null
    private var properties: List<PropertyEntity?> = mutableListOf()

    override fun onStart() {
        super.onStart()
        with(binding) {
            if (auth.currentUser != null) {

                val storagePath = FirebaseStorage.getInstance()
                    .reference
                    .child("${auth.currentUser?.email}/${auth.currentUser?.email}")

                storagePath.downloadUrl.addOnSuccessListener {
                    authImg.load(it)
                }

                authButton.setOnClickListener {
                    navController.navigate(R.id.action_mainFragment_to_accountAgentFragment)
                }
            } else {

                authImg.load(R.drawable.account_circle_icon)

                authButton.setOnClickListener {
                    navController.navigate(R.id.action_mainFragment_to_authFragment)
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        RealEstateApplication.instance.appComponent.inject(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)
        auth = FirebaseAuth.getInstance()
        email = auth.currentUser?.email

        subscribeToLiveData()

        buttonsInit()

        setProperties()
    }

    private fun setProperties() {
        CoroutineScope(Main).launch {
            val job = async(IO) {
                properties = viewModel.getAllProperties()
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
                    Log.d("TAG", "onItemClick: ${property.id}")
                }
                navController.navigate(R.id.action_mainFragment_to_detailsFragment, bundle)
            }
        }, viewModel)
        propertiesAdapter.setProperties(properties)
        mainRecyclerView.adapter = propertiesAdapter
        mainRecyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
    }

    /*private fun loggedIn() = with(binding) {
        email = getEmail()
        if (email == null || email == "") {

            authImg.load(R.drawable.account_circle_icon)

        } else {
            CoroutineScope(Main).launch {
                val job = async(IO) {
                    viewModel.getAgentByEmail(email!!)
                }
                job.await()
                val uri = Uri.parse(agent!!.profileImg)
                authImg.load(uri)
                authButton.setOnClickListener {
                    navController.navigate(R.id.action_mainFragment_to_accountAgentFragment)
                }
            }
        }
    }*/

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