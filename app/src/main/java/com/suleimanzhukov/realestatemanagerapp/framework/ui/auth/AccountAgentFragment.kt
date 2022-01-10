package com.suleimanzhukov.realestatemanagerapp.framework.ui.auth

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import coil.load
import com.suleimanzhukov.realestatemanagerapp.R
import com.suleimanzhukov.realestatemanagerapp.databinding.FragmentAccountAgentBinding
import com.suleimanzhukov.realestatemanagerapp.framework.MainActivity
import com.suleimanzhukov.realestatemanagerapp.model.utils.Agent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class AccountAgentFragment : Fragment() {

    private var _binding: FragmentAccountAgentBinding? = null
    private val binding get() = _binding!!

    private lateinit var mainActivity: MainActivity
    private lateinit var navController: NavController

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
        mainActivity = activity as MainActivity
        navController = Navigation.findNavController(view)

        viewModel.getAgentLiveData().observe(viewLifecycleOwner, Observer {
            agent = viewModel.getAgentLiveData().value
        })

        logOutButton.setOnClickListener {
            logoutAgentByEmail()
        }

        profileImage.setOnClickListener {
            checkPermissions()
        }
    }

    private fun checkPermissions() {
        if (mainActivity.checkReadStoragePermission()) {
            uploadProfileImage()
        } else {
            mainActivity.requestReadStoragePermission()
        }
    }

    private fun uploadProfileImage() = with(binding) {
        val intent = Intent().apply {
            action = Intent.ACTION_PICK
            type = "image/*"
        }

        getImageResult.launch(intent)
    }

    private val getImageResult = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {
        if (it.resultCode == Activity.RESULT_OK) {
            val preferences = activity?.getSharedPreferences(SignUpFragment.SHARED_TAG, Context.MODE_PRIVATE)
            val email = preferences?.getString(SignUpFragment.EMAIL_TAG, "")
            CoroutineScope(Main).launch {
                val getAgentJob = async(IO) {
                    viewModel.getAgentByEmail(email!!, requireContext())
                }
                getAgentJob.await()
                val agent = viewModel.getAgentLiveData().value
                val updateJob = async(IO) {
                    viewModel.updateAgent(agent!!, requireContext())
                }
                updateJob.await()
                binding.profileImage.load(it.data?.data)
            }
        }
    }

    @SuppressLint("CommitPrefEdits")
    private fun logoutAgentByEmail() {
        val preferencesEditor = activity?.getSharedPreferences(SignUpFragment.SHARED_TAG, Context.MODE_PRIVATE)?.edit()
        preferencesEditor?.remove(SignUpFragment.USERNAME_TAG)
        preferencesEditor?.remove(SignUpFragment.EMAIL_TAG)
        preferencesEditor?.apply()

        navController.navigate(R.id.action_accountAgentFragment_to_mainFragment)
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