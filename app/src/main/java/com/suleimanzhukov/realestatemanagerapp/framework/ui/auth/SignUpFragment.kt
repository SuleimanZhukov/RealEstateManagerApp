package com.suleimanzhukov.realestatemanagerapp.framework.ui.auth

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.suleimanzhukov.realestatemanagerapp.R
import com.suleimanzhukov.realestatemanagerapp.databinding.FragmentSignUpBinding
import com.suleimanzhukov.realestatemanagerapp.model.database.entities.AgentEntity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import javax.inject.Inject

class SignUpFragment : Fragment() {

    private var _binding: FragmentSignUpBinding? = null
    private val binding get() = _binding!!

    private lateinit var navController: NavController

    @Inject
    private lateinit var viewModel: AuthViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentSignUpBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) = with(binding) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)
        signUpButton.setOnClickListener {
            signUpButton(
                signUpNameEditText.text.toString(),
                signUpEmailEditText.text.toString(),
                signUpPasswordEditText.text.toString(),
                signUpConfirmPasswordEditText.text.toString()
            )
        }
    }

    @SuppressLint("CommitPrefEdits")
    private fun signUpButton(username: String, email: String, password: String, confirmPassword: String): Unit = with(binding) {
        if (username.isBlank()) {
            Toast.makeText(context, "Type your name", Toast.LENGTH_SHORT).show()
            return
        } else if (!email.contains('@') || !email.contains('.')) {
            Toast.makeText(context, "Email incorrect", Toast.LENGTH_SHORT).show()
            return
        } else if (password != confirmPassword) {
            Toast.makeText(context, "Confirm password", Toast.LENGTH_SHORT).show()
            return
        }

        val agent = AgentEntity(0, username, "", email, password, "",  "")

        val bundle = Bundle().apply {
            putParcelable(AccountAgentFragment.AGENT_KEY, agent)
        }

        CoroutineScope(Main).launch {
            val job = async(IO) {
                viewModel.registerAgent(agent, requireContext())
            }
            job.await()
        }

        val preferencesEditor = activity?.getSharedPreferences(SHARED_TAG, Context.MODE_PRIVATE)?.edit()
        preferencesEditor?.putString(USERNAME_TAG, username)
        preferencesEditor?.putString(EMAIL_TAG, email)
        preferencesEditor?.apply()

        navController.navigate(R.id.action_signUpFragment_to_accountAgentFragment)
    }


    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    companion object {
        const val SHARED_TAG = "shared_tag"
        const val EMAIL_TAG = "email_tag"
        const val USERNAME_TAG = "user_tag"

        fun newInstance() = SignUpFragment()
    }
}