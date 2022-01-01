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
import com.suleimanzhukov.realestatemanagerapp.databinding.FragmentAuthBinding
import com.suleimanzhukov.realestatemanagerapp.model.utils.Agent

class AuthFragment : Fragment() {

    private var _binding: FragmentAuthBinding? = null
    private val binding get() = _binding!!

    private val viewModel: AuthViewModel by lazy {
        ViewModelProvider(this).get(AuthViewModel::class.java)
    }

    private var inAgent: Agent? = null
    private var inPassword: String? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentAuthBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        loginInit()
        signUpInit()
    }

    @SuppressLint("CommitPrefEdits")
    private fun loginInit() = with(binding) {
        subscribeToLiveData()

        loginButton.setOnClickListener {
            var email = loginEmailEditText.text.toString()
            var password = loginPasswordEditText.text.toString()

            viewModel.getAgentByEmail(email, requireContext())
            val agent = inAgent

            if (agent == null) {
                Toast.makeText(context, "This email is not registered...\nTry different one or sign up.", Toast.LENGTH_LONG).show()
            } else {
                viewModel.getPasswordByEmail(email, requireContext())
                if (inPassword == password) {
                    val preferencesEditor = activity?.getSharedPreferences(SignUpFragment.SHARED_TAG, Context.MODE_PRIVATE)?.edit()
                    preferencesEditor?.putString(SignUpFragment.USERNAME_TAG, agent?.username)
                    preferencesEditor?.putString(SignUpFragment.EMAIL_TAG, email)

                    requireActivity().supportFragmentManager
                        .beginTransaction()
                        .replace(R.id.container_fragment_main, AccountAgentFragment.newInstanceEmpty())
                } else {
                    Toast.makeText(context, "Wrong password", Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    private fun subscribeToLiveData() {
        viewModel.getAgentLiveData().observe(viewLifecycleOwner, Observer {
            inAgent = viewModel.getAgentLiveData().value
        })

        viewModel.getPasswordLiveData().observe(viewLifecycleOwner, Observer {
            inPassword = viewModel.getPasswordLiveData().value
        })
    }

    private fun signUpInit() = with(binding) {
        loginSignUpTextView.setOnClickListener {
            fragmentInit(SignUpFragment.newInstance())
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
        fun newInstance() = AuthFragment()
    }
}