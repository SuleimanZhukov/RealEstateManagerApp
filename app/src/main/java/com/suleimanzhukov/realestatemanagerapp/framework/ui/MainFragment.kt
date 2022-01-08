package com.suleimanzhukov.realestatemanagerapp.framework.ui

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import coil.load
import com.suleimanzhukov.realestatemanagerapp.R
import com.suleimanzhukov.realestatemanagerapp.databinding.FragmentMainBinding
import com.suleimanzhukov.realestatemanagerapp.framework.ui.auth.SignUpFragment

class MainFragment : Fragment() {

    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!

    private lateinit var navController: NavController

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)
        isLoggedIn()
        buttonsInit()
    }

    private fun isLoggedIn() = with(binding) {
        val preferencesEditor = activity?.getSharedPreferences(SignUpFragment.SHARED_TAG, Context.MODE_PRIVATE)
        val email = preferencesEditor?.getString(SignUpFragment.EMAIL_TAG, "")

        if (email == null || email == "") {
            authButton.setOnClickListener {
                navController.navigate(R.id.action_mainFragment_to_authFragment)
                /*requireActivity().supportFragmentManager
                    .beginTransaction()
                    .add(R.id.container_fragment_main, AuthFragment.newInstance())
                    .commitAllowingStateLoss()*/
            }
        } else {
            authImg.load(R.drawable.profile_image)
            authButton.setOnClickListener {
                navController.navigate(R.id.action_mainFragment_to_accountAgentFragment)
//                fragmentInit(AccountAgentFragment.newInstanceEmpty())
            }
        }
    }

    private fun buttonsInit() = with(binding) {
        settingsButton.setOnClickListener {
            navController.navigate(R.id.action_mainFragment_to_settingsFragment)
//            fragmentInit(SettingsFragment.newInstance())
        }
        searchButton.setOnClickListener {
            navController.navigate(R.id.action_mainFragment_to_agentsListFragment)
//            fragmentInit(AgentsListFragment.newInstance())
        }
    }

    private fun fragmentInit(fragment: Fragment) {
        requireActivity().supportFragmentManager
            .beginTransaction()
            .add(R.id.container_fragment_main, fragment)
            .addToBackStack("")
            .commitAllowingStateLoss()
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    companion object {
        fun newInstance() = MainFragment()
    }
}