package com.suleimanzhukov.realestatemanagerapp.framework.ui.auth

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.suleimanzhukov.realestatemanagerapp.R
import com.suleimanzhukov.realestatemanagerapp.databinding.FragmentPublishBinding
import com.suleimanzhukov.realestatemanagerapp.di.DaggerRealEstateComponent
import com.suleimanzhukov.realestatemanagerapp.model.database.entities.PropertyEntity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import javax.inject.Inject

class PublishFragment : Fragment() {

    private var _binding: FragmentPublishBinding? = null
    private val binding get() = _binding!!

    private lateinit var navController: NavController

    @Inject
    lateinit var viewModel: AuthViewModel

    override fun onAttach(context: Context) {
        super.onAttach(context)
        DaggerRealEstateComponent.builder().build().getForPublishFragment(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentPublishBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)

        onPublish()
    }

    private fun onPublish() = with(binding) {
        publishButton.setOnClickListener {
            val publisher = getUsername()!!
            val price = priceInputEditText.text.toString().toInt()
            val address = "${provinceInputEditText.text.toString()}, ${cityInputEditText.text.toString()}," +
                    " ${firstAddressInputEditText.text.toString()}, ${secondAddressInputEditText.text.toString()}"
            val type = typeInputEditText.text.toString()
            val timePublished = ""
            val beds = bedsInputEditText.text.toString().toInt()
            val baths = bathsInputEditText.text.toString().toInt()
            val garages = garagesInputEditText.text.toString().toInt()
            val details = publishDetailsTextView.text.toString()
            val area = squareInputEditText.text.toString().toInt()
            val images = ""
            val location = ""

            val property = PropertyEntity(0, publisher, price, address, type, timePublished, beds, baths, garages, area, details,
            images, location)

            var properties: List<PropertyEntity?> = mutableListOf()

            CoroutineScope(Dispatchers.Main).launch {
                val job = async(IO) {
                    viewModel.addProperty(property, requireContext())
                }
                job.await()
                navController.navigate(R.id.action_publishFragment_to_mainFragment)

                val getJobs = async(IO) {
                    properties = viewModel.getAllProperties(requireContext())
                }
                getJobs.await()
                Log.d("TAG", "onPublish: ${properties.get(0)?.address}")
            }
        }
    }

    private fun getUsername(): String? {
        val preferencesEditor = activity?.getSharedPreferences(SignUpFragment.SHARED_TAG, Context.MODE_PRIVATE)
        return preferencesEditor?.getString(SignUpFragment.USERNAME_TAG, "")
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}