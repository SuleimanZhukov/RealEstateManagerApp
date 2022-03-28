package com.suleimanzhukov.realestatemanagerapp.framework.ui.auth

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.Navigation
import coil.load
import com.suleimanzhukov.realestatemanagerapp.R
import com.suleimanzhukov.realestatemanagerapp.RealEstateApplication
import com.suleimanzhukov.realestatemanagerapp.databinding.FragmentAccountAgentBinding
import com.suleimanzhukov.realestatemanagerapp.framework.MainActivity
import com.suleimanzhukov.realestatemanagerapp.model.database.entities.AgentEntity
import com.suleimanzhukov.realestatemanagerapp.model.database.entities.PropertyEntity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import javax.inject.Inject

class AccountAgentFragment : Fragment() {

    private var _binding: FragmentAccountAgentBinding? = null
    private val binding get() = _binding!!

    private lateinit var mainActivity: MainActivity
    private lateinit var navController: NavController

    @Inject
    lateinit var viewModel: AuthViewModel

    private var agent: AgentEntity? = null
    private var email: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        RealEstateApplication.instance.appComponent.inject(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentAccountAgentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?): Unit = with(binding) {
        super.onViewCreated(view, savedInstanceState)
        mainActivity = activity as MainActivity
        navController = Navigation.findNavController(view)

        logOutButton.setOnClickListener {
            logoutAgentByEmail()
        }

        email = getEmail()
        setProfile(profileImage)

        subscribeToLiveData()

        profileImage.setOnClickListener {
            checkPermissions()
        }

        onAddPropertyButtonPress()
        backButtonPress()
    }

    private fun onAddPropertyButtonPress() = with(binding) {
        addPropertyButtonView.setOnClickListener {
            navController.navigate(R.id.action_accountAgentFragment_to_publishFragment)
        }
        addButtonPlusSign.setOnClickListener {
            navController.navigate(R.id.action_accountAgentFragment_to_publishFragment)
        }
    }

    private fun backButtonPress() = with(binding) {
        profileArrowBack.setOnClickListener {
            mainActivity.onBackPressed()
        }
        profileBackButtonView.setOnClickListener {
            mainActivity.onBackPressed()
        }
    }

    private fun addPhoneToDB(phone: String) {
        CoroutineScope(Main).launch {
            val job = async(IO) {
                viewModel.updateAgent(agent!!)
            }
            job.await()
            val getJob = async(IO) {
                viewModel.getAgentByEmail(email!!)
            }
            getJob.await()
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
            CoroutineScope(Main).launch {
                val getAgentJob = async(IO) {
                    viewModel.getAgentByEmail(email!!)
                }
                getAgentJob.await()
                agent?.profileImg = it.data?.data.toString()

                val updateJob = async(IO) {
                    viewModel.updateAgent(agent!!)
                }
                updateJob.await()

                val getJob = async(IO) {
                    viewModel.getAgentByEmail(email!!)
                }
                getJob.await()
                val uri = Uri.parse(agent?.profileImg)
                binding.profileImage.load(uri)
            }
        }
    }

    private fun getEmail(): String? {
        val preferences = activity?.getSharedPreferences(SignUpFragment.SHARED_TAG, Context.MODE_PRIVATE)
        return preferences?.getString(SignUpFragment.EMAIL_TAG, "")
    }

    @SuppressLint("CommitPrefEdits")
    private fun logoutAgentByEmail() {
        val preferencesEditor = activity?.getSharedPreferences(SignUpFragment.SHARED_TAG, Context.MODE_PRIVATE)?.edit()
        preferencesEditor?.remove(SignUpFragment.USERNAME_TAG)
        preferencesEditor?.remove(SignUpFragment.EMAIL_TAG)
        preferencesEditor?.apply()

        navController.navigate(R.id.action_accountAgentFragment_to_mainFragment)
    }

    private fun subscribeToLiveData() {
        viewModel.getAgentLiveData().observe(viewLifecycleOwner, Observer {
            agent = viewModel.getAgentLiveData().value
        })
    }

    private fun setProfile(profileImage: ImageView) = with(binding) {
        CoroutineScope(Main).launch {
            val job = async(IO) {
                viewModel.getAgentByEmail(email!!)
            }
            job.await()
            val uri = Uri.parse(agent?.profileImg)
            profileImage.load(uri)

            profileAgentNameTextView.text = agent?.username

            var propertyList = mutableListOf<PropertyEntity>()
            val propertiesJob = async(IO) {
                propertyList = viewModel.getAllPropertiesWithAgent(email!!)
            }
            propertiesJob.await()
            greyAreaForSaleNumberTextView.text = "${propertyList.size}"
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        const val AGENT_KEY = "agent_key_for_bundle"

        fun newInstanceEmpty() = AccountAgentFragment()

        fun newInstance(bundle: Bundle): AccountAgentFragment {
            var fragment = AccountAgentFragment()
            fragment.arguments = bundle
            return fragment
        }
    }
}