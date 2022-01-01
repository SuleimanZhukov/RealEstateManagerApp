package com.suleimanzhukov.realestatemanagerapp.framework.ui

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import coil.load
import com.suleimanzhukov.realestatemanagerapp.R
import com.suleimanzhukov.realestatemanagerapp.databinding.FragmentMainBinding
import com.suleimanzhukov.realestatemanagerapp.framework.ui.agentslist.AgentsListFragment
import com.suleimanzhukov.realestatemanagerapp.framework.ui.auth.AccountAgentFragment
import com.suleimanzhukov.realestatemanagerapp.framework.ui.auth.AuthFragment
import com.suleimanzhukov.realestatemanagerapp.framework.ui.auth.SignUpFragment

class MainFragment : Fragment() {

    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        isLoggedIn()
        buttonsInit()
    }

    private fun isLoggedIn() = with(binding) {
        val preferencesEditor = activity?.getSharedPreferences(SignUpFragment.SHARED_TAG, Context.MODE_PRIVATE)
        val email = preferencesEditor?.getString(SignUpFragment.EMAIL_TAG, "")

        if (email == null || email == "") {
            authButton.setOnClickListener {
                fragmentInit(AuthFragment.newInstance())
            }
        } else {
            authImg.load(R.drawable.profile_image)
            authButton.setOnClickListener {
                fragmentInit(AccountAgentFragment.newInstanceEmpty())
            }
        }
    }

    private fun buttonsInit() = with(binding) {
        settingsButton.setOnClickListener {
            fragmentInit(SettingsFragment.newInstance())
        }
        searchButton.setOnClickListener {
            fragmentInit(AgentsListFragment.newInstance())
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