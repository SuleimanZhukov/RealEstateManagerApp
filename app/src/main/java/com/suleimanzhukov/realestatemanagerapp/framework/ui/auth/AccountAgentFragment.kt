package com.suleimanzhukov.realestatemanagerapp.framework.ui.auth

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.suleimanzhukov.realestatemanagerapp.R
import com.suleimanzhukov.realestatemanagerapp.databinding.FragmentAccountAgentBinding
import com.suleimanzhukov.realestatemanagerapp.framework.ui.MainFragment
import com.suleimanzhukov.realestatemanagerapp.model.utils.Agent

class AccountAgentFragment : Fragment() {

    private var _binding: FragmentAccountAgentBinding? = null
    private val binding get() = _binding!!

    private val viewModel: AuthViewModel by lazy {
        ViewModelProvider(this).get(AuthViewModel::class.java)
    }

    private var agent: Agent? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentAccountAgentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) = with(binding) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getAgentLiveData().observe(viewLifecycleOwner, Observer {
            agent = viewModel.getAgentLiveData().value
        })

        logOutButton.setOnClickListener {
            logoutAgentByEmail()
        }
    }

    @SuppressLint("CommitPrefEdits")
    private fun logoutAgentByEmail() {
        val preferencesEditor = activity?.getSharedPreferences(SignUpFragment.SHARED_TAG, Context.MODE_PRIVATE)?.edit()
        preferencesEditor?.remove(SignUpFragment.USERNAME_TAG)
        preferencesEditor?.remove(SignUpFragment.EMAIL_TAG)
        preferencesEditor?.apply()

        requireActivity().supportFragmentManager
            .beginTransaction()
            .replace(R.id.container_fragment_main, MainFragment.newInstance())
            .commitAllowingStateLoss()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        const val AGENT_KEY = "agent_key_to_add"

        fun newInstanceEmpty() = AccountAgentFragment()

        fun newInstance(bundle: Bundle): AccountAgentFragment {
            var fragment = AccountAgentFragment()
            fragment.arguments = bundle
            return fragment
        }
    }
}