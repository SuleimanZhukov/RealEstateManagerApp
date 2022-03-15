package com.suleimanzhukov.realestatemanagerapp.framework.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.suleimanzhukov.realestatemanagerapp.RealEstateApplication
import com.suleimanzhukov.realestatemanagerapp.databinding.FragmentDetailsBinding
import com.suleimanzhukov.realestatemanagerapp.framework.MainActivity
import com.suleimanzhukov.realestatemanagerapp.framework.ui.adapters.DetailsListAdapter
import com.suleimanzhukov.realestatemanagerapp.framework.ui.auth.AuthViewModel
import com.suleimanzhukov.realestatemanagerapp.model.database.entities.PropertyEntity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import javax.inject.Inject

class DetailsFragment : Fragment() {

    private var _binding: FragmentDetailsBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var viewModel: AuthViewModel

    private lateinit var mainActivity: MainActivity
    private lateinit var navController: NavController

    private var property: PropertyEntity? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        RealEstateApplication.instance.appComponent.inject(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mainActivity = activity as MainActivity
        navController = Navigation.findNavController(view)

        getPropertyDetails()
        backButtonPress()
    }

    private fun getPropertyDetails() = with(binding) {
        val id = requireArguments().getString("receiver")

        CoroutineScope(Main).launch {
            val job = async(IO) {
                property = viewModel.getPropertyById(id!!.toLong())
                Log.d("TAG", "getPropertyDetails: ${property!!.garages}")
            }
            job.await()
            Log.d("TAG", "getPropertyDetails: ${property!!.area}")
            detailsPriceTextView.text = property!!.price.toString()
            detailsAddressTextView.text = property!!.address
            detailsDescriptionTextView.text = property!!.details
            setAdapter()
        }
    }

    private fun setAdapter() = with(binding) {
        val detailsAdapter = DetailsListAdapter()
        Log.d("TAG", "getPropertyDetails: ${property!!.area}")
        detailsAdapter.setDetails(property)
        detailsRecyclerViewInfo.adapter = detailsAdapter
        detailsRecyclerViewInfo.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
    }

    private fun backButtonPress() = with(binding) {
        detailsArrowBack.setOnClickListener {
            mainActivity.onBackPressed()
        }
        backButtonView.setOnClickListener {
            mainActivity.onBackPressed()
        }
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    companion object {
        fun newInstance() = DetailsFragment()
    }
}