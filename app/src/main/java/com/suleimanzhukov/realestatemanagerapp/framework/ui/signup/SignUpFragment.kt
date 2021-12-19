package com.suleimanzhukov.realestatemanagerapp.framework.ui.signup

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.suleimanzhukov.realestatemanagerapp.databinding.FragmentSignUpBinding
import com.suleimanzhukov.realestatemanagerapp.framework.MainActivity

class SignUpFragment : Fragment() {

    private var _binding: FragmentSignUpBinding? = null
    private val binding get() = _binding!!

    private lateinit var mainActivity: MainActivity

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentSignUpBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mainActivity = activity as MainActivity
        with(binding) {
            createAgent()
        }
    }

    private fun createAgent() = with(binding) {
        signUpButton.setOnClickListener {
            if (signUpNameEditText.toString() != "" && signUpEmailEditText.toString() != ""
                && signUpEmailEditText.toString().contains('@')
                && signUpPasswordEditText.toString() == signUpConfirmPasswordEditText.toString()) {
                createAccount(signUpEmailEditText.toString(), signUpPasswordEditText.toString())
            } else {
                Toast.makeText(context, "Fill the stuff, man...", Toast.LENGTH_SHORT).show()
            }
        }
    }


    private fun createAccount(email: String, password: String) {
        mainActivity.getAuth().createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener {
                Toast.makeText(context, "Signed up successfully", Toast.LENGTH_SHORT).show()
            }.addOnFailureListener {
                Toast.makeText(context, "Signed up FAILED", Toast.LENGTH_SHORT).show()
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