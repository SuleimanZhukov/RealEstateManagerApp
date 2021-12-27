package com.suleimanzhukov.realestatemanagerapp.framework.ui.signup

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.suleimanzhukov.realestatemanagerapp.R
import com.suleimanzhukov.realestatemanagerapp.databinding.FragmentSignUpBinding
import com.suleimanzhukov.realestatemanagerapp.framework.ui.accountAgent.AccountAgentFragment
import com.suleimanzhukov.realestatemanagerapp.model.utils.Agent

class SignUpFragment : Fragment() {

    private var _binding: FragmentSignUpBinding? = null
    private val binding get() = _binding!!

    private val viewModel: SignUpViewModel by lazy {
        ViewModelProvider(this).get(SignUpViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentSignUpBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) = with(binding) {
        super.onViewCreated(view, savedInstanceState)
        signUpButton.setOnClickListener {
            viewModel.getSignUpLiveData().observe(viewLifecycleOwner, Observer {
                sigUpButton(
                    signUpNameEditText.text.toString(),
                    signUpEmailEditText.text.toString(),
                    signUpPasswordEditText.text.toString(),
                    signUpConfirmPasswordEditText.text.toString()
                )
            })
        }
    }

    private fun sigUpButton(name: String, email: String, password: String, confirmPassword: String) = with(binding) {
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

        val agent = Agent(0, name, "", email, password, "", false, "")

        val bundle = Bundle().apply {
            putParcelable(AccountAgentFragment.AGENT_KEY, agent)
        }
        signUpButton.setOnClickListener {
            viewModel.registerAgent(agent)
            requireActivity().supportFragmentManager
                .beginTransaction()
                .replace(R.id.container_fragment_main, AccountAgentFragment.newInstance(bundle))
        }
    }


    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    companion object {
        fun newInstance() = SignUpFragment()
    }
}