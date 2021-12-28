package com.suleimanzhukov.realestatemanagerapp.framework.ui.auth

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.suleimanzhukov.realestatemanagerapp.R
import com.suleimanzhukov.realestatemanagerapp.databinding.FragmentSignUpBinding
import com.suleimanzhukov.realestatemanagerapp.framework.MainActivity
import com.suleimanzhukov.realestatemanagerapp.model.utils.Agent
import java.util.prefs.Preferences

class SignUpFragment : Fragment() {

    private var _binding: FragmentSignUpBinding? = null
    private val binding get() = _binding!!

    private val viewModel: AuthViewModel by lazy {
        ViewModelProvider(this).get(AuthViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentSignUpBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) = with(binding) {
        super.onViewCreated(view, savedInstanceState)
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
    private fun signUpButton(name: String, email: String, password: String, confirmPassword: String): Unit = with(binding) {
        if (name.isBlank()) {
            Toast.makeText(context, "Type your name", Toast.LENGTH_SHORT).show()
            return
        } else if (!email.contains('@') || !email.contains('.')) {
            Toast.makeText(context, "Email incorrect", Toast.LENGTH_SHORT).show()
            return
        } else if (password != confirmPassword) {
            Toast.makeText(context, "Confirm password", Toast.LENGTH_SHORT).show()
            return
        }

        val agent = Agent(name, "", email, password, "", true, "")

        val bundle = Bundle().apply {
            putParcelable(AccountAgentFragment.AGENT_KEY, agent)
        }

        viewModel.registerAgent(agent, requireContext())

        val preferencesEditor = activity?.getSharedPreferences(SHARED_TAG, Context.MODE_PRIVATE)?.edit()
        preferencesEditor?.putString(EMAIL_TAG, email)

        requireActivity().supportFragmentManager
            .beginTransaction()
            .replace(R.id.container_fragment_main, AccountAgentFragment.newInstance(bundle))
            .commitNowAllowingStateLoss()
    }


    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    companion object {
        const val SHARED_TAG = "shared_tag"
        const val EMAIL_TAG = "email_tag"

        fun newInstance() = SignUpFragment()
    }
}