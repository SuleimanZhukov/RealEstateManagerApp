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
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.suleimanzhukov.realestatemanagerapp.R
import com.suleimanzhukov.realestatemanagerapp.databinding.FragmentAuthBinding
import com.suleimanzhukov.realestatemanagerapp.model.database.entities.AgentEntity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import javax.inject.Inject

class AuthFragment : Fragment() {

    private var _binding: FragmentAuthBinding? = null
    private val binding get() = _binding!!

    private lateinit var navController: NavController

    @Inject
    lateinit var viewModel: AuthViewModel

    private var inAgent: AgentEntity? = null
    private var inPassword: String? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentAuthBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)
        loginInit()
        signUpInit()
    }

    @SuppressLint("CommitPrefEdits")
    private fun loginInit() = with(binding) {
        subscribeToLiveData()

        loginButton.setOnClickListener {
            var email = loginEmailEditText.text.toString()
            var password = loginPasswordEditText.text.toString()

            CoroutineScope(Main).launch {
                val job = async(IO) {
                    viewModel.getAgentByEmail(email, requireContext())
                }
                job.await()
                val agent = inAgent

                if (agent == null) {
                    Toast.makeText(context, "This email is not registered...\nTry different one or sign up.", Toast.LENGTH_SHORT).show()
                } else {
                    val jobPassword = async(IO) {
                        viewModel.getPasswordByEmail(email, requireContext())
                    }
                    jobPassword.await()
                    if (inPassword == password) {
                        val preferencesEditor = activity?.getSharedPreferences(SignUpFragment.SHARED_TAG, Context.MODE_PRIVATE)?.edit()
                        preferencesEditor?.putString(SignUpFragment.USERNAME_TAG, agent.username)
                        preferencesEditor?.putString(SignUpFragment.EMAIL_TAG, email)
                        preferencesEditor?.apply()

                        navController.navigate(R.id.action_authFragment_to_accountAgentFragment)
                    } else {
                        Toast.makeText(context, "Wrong password", Toast.LENGTH_SHORT).show()
                    }
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
            navController.navigate(R.id.action_authFragment_to_signUpFragment)
        }
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    companion object {
        fun newInstance() = AuthFragment()
    }
}