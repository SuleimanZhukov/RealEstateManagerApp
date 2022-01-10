package com.suleimanzhukov.realestatemanagerapp.framework.ui.main

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import coil.load
import com.suleimanzhukov.realestatemanagerapp.R
import com.suleimanzhukov.realestatemanagerapp.databinding.FragmentMainBinding
import com.suleimanzhukov.realestatemanagerapp.framework.ui.auth.AuthViewModel
import com.suleimanzhukov.realestatemanagerapp.framework.ui.auth.SignUpFragment
import com.suleimanzhukov.realestatemanagerapp.model.utils.Agent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class MainFragment : Fragment() {

    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!

    private val viewModel: MainViewModel by lazy {
        ViewModelProvider(this).get(MainViewModel::class.java)
    }

    private lateinit var navController: NavController

    private var agent: Agent? = null
    private lateinit var profileImage: String

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
    }

    private fun isLoggedIn() = with(binding) {
        val preferencesEditor = activity?.getSharedPreferences(SignUpFragment.SHARED_TAG, Context.MODE_PRIVATE)
        val email = preferencesEditor?.getString(SignUpFragment.EMAIL_TAG, "")

        if (email == null || email == "") {
            authButton.setOnClickListener {
                authImg.load(R.drawable.account_circle_icon)
                navController.navigate(R.id.action_mainFragment_to_authFragment)
            }
        } else {
            CoroutineScope(Main).launch {
                val job = async(IO) {
                    viewModel.getAgentByEmail(email, requireContext())
                }
                job.await()
                authImg.load(profileImage)
                authButton.setOnClickListener {
                    navController.navigate(R.id.action_mainFragment_to_accountAgentFragment)
                }
            }
        }
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
            profileImage = agent!!.profileImg
        })
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    companion object {
        fun newInstance() = MainFragment()
    }
}