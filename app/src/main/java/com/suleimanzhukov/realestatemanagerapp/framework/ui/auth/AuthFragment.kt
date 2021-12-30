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
import java.lang.Exception

class AuthFragment : Fragment() {

    private var _binding: FragmentAuthBinding? = null
    private val binding get() = _binding!!

    private val viewModel: AuthViewModel by lazy {
        ViewModelProvider(this).get(AuthViewModel::class.java)
    }

    private lateinit var inAgent: Agent
    private lateinit var inPassword: String

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentAuthBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initLiveData()

        loginInit()
        signUpInit()
    }

    @SuppressLint("CommitPrefEdits")
    private fun loginInit() = with(binding) {
        loginButton.setOnClickListener {
            val email = loginEmailEditText.toString()
            val password = loginPasswordEditText.toString()

            val agent = viewModel.getAgentByEmail(email, requireContext())

            if (agent == null) {
                Toast.makeText(context, "This email is not registered...\nTry different one or sign up.", Toast.LENGTH_SHORT).show()
            } else {
                if (viewModel.getPasswordByEmail(email, requireContext()) == password) {
                    val preferencesEditor = activity?.getSharedPreferences(SignUpFragment.SHARED_TAG, Context.MODE_PRIVATE)?.edit()
                    preferencesEditor?.putString(SignUpFragment.USERNAME_TAG, agent.username)
                    preferencesEditor?.putString(SignUpFragment.EMAIL_TAG, email)
                } else {
                    Toast.makeText(context, "Wrong password", Toast.LENGTH_SHORT).show()
                }
            }

            /*try {
                viewModel.getAgentByEmail(email, requireContext())
            } catch (e: Exception) {
                Toast.makeText(context, "This email is not registered...\nTry different one or sign up.", Toast.LENGTH_SHORT).show()
            }*/
        }
    }

    private fun initLiveData() {
        viewModel.getAgentLiveData().observe(viewLifecycleOwner, Observer {
            inAgent
        })

        viewModel.getPasswordLiveData().observe(viewLifecycleOwner, Observer {

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