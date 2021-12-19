package com.suleimanzhukov.realestatemanagerapp.framework.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.suleimanzhukov.realestatemanagerapp.R
import com.suleimanzhukov.realestatemanagerapp.databinding.FragmentAuthBinding
import com.suleimanzhukov.realestatemanagerapp.framework.ui.signup.SignUpFragment

class AuthFragment : Fragment() {

    private var _binding: FragmentAuthBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentAuthBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        signUpInit()
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