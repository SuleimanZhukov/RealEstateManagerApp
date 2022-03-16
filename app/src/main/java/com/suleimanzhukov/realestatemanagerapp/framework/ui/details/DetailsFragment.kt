package com.suleimanzhukov.realestatemanagerapp.framework.ui.details

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.smarteist.autoimageslider.SliderView
import com.suleimanzhukov.realestatemanagerapp.R
import com.suleimanzhukov.realestatemanagerapp.RealEstateApplication
import com.suleimanzhukov.realestatemanagerapp.databinding.FragmentDetailsBinding
import com.suleimanzhukov.realestatemanagerapp.framework.MainActivity
import com.suleimanzhukov.realestatemanagerapp.framework.ui.adapters.DetailsListAdapter
import com.suleimanzhukov.realestatemanagerapp.framework.ui.adapters.DetailsSliderAdapter
import com.suleimanzhukov.realestatemanagerapp.framework.ui.adapters.OtherPropertiesAdapter
import com.suleimanzhukov.realestatemanagerapp.framework.ui.auth.AuthViewModel
import com.suleimanzhukov.realestatemanagerapp.framework.ui.main.MainFragment
import com.suleimanzhukov.realestatemanagerapp.model.database.entities.PictureEntity
import com.suleimanzhukov.realestatemanagerapp.model.database.entities.PropertyEntity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import java.util.function.Predicate
import javax.inject.Inject

class DetailsFragment : Fragment() {

    private var _binding: FragmentDetailsBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var viewModel: AuthViewModel

    private lateinit var mainActivity: MainActivity
    private lateinit var navController: NavController

    private var property: PropertyEntity? = null
    private var otherProperties: MutableList<PropertyEntity> = mutableListOf()
    private var pictures: List<PictureEntity> = mutableListOf()
    private var agentEmail: String? = null

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
            detailsPriceTextView.text = "$${property!!.price}"
            detailsAddressTextView.text = property!!.address
            detailsDescriptionTextView.text = property!!.details
            detailsAgentNameTextView.text = property!!.publisherUsername
            agentEmail = property!!.publisher
            setInfoAdapter()
            getOtherProperties()
        }
    }

    private fun setInfoAdapter() = with(binding) {
        val detailsAdapter = DetailsListAdapter()
        Log.d("TAG", "getPropertyDetails: ${property!!.area}")
        detailsAdapter.setDetails(property)
        detailsRecyclerViewInfo.adapter = detailsAdapter
        detailsRecyclerViewInfo.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
    }

    private fun setSliderAdapter() = with(binding) {
        val sliderAdapter = DetailsSliderAdapter(requireContext(), pictures) //TODO
        detailsSliderView.setAutoCycleDirection(SliderView.LAYOUT_DIRECTION_LTR)
        detailsSliderView.setSliderAdapter(sliderAdapter)
    }

    private fun getPictures() = with(binding) {
        CoroutineScope(Main).launch {
            val job = async(IO) {
                pictures = viewModel.getAllPicturesForPropertyId(property!!.id)
            }
            job.await()
            setSliderAdapter()
        }
    }

    private fun getOtherProperties() = with(binding) {
        CoroutineScope(Main).launch {
            val job = async(IO) {
                otherProperties = viewModel.getAllPropertiesWithAgent(agentEmail!!)
            }
            job.await()
            setOtherPropertiesAdapter()
        }
    }

    private fun setOtherPropertiesAdapter() = with(binding) {
        val otherPropertiesAdapter = OtherPropertiesAdapter(object : MainFragment.OnAdapterItemClickListener {
            override fun onItemClick(property: PropertyEntity, position: Int) {
                val bundle = Bundle().apply {
                    putString("receiver", property.id.toString())
                    Log.d("TAG", "onItemClick: ${property.id.toString()}")
                }
                navController.navigate(R.id.action_detailsFragment_self, bundle)
            }

        })

        addOtherProperties(otherPropertiesAdapter)

        detailsRecyclerViewOtherProperties.adapter = otherPropertiesAdapter
        detailsRecyclerViewOtherProperties.layoutManager = LinearLayoutManager(
            context,
            LinearLayoutManager.HORIZONTAL,
            false)
    }

    private fun backButtonPress() = with(binding) {
        detailsArrowBack.setOnClickListener {
            mainActivity.onBackPressed()
        }
        backButtonView.setOnClickListener {
            mainActivity.onBackPressed()
        }
    }

    private fun addOtherProperties(otherPropertiesAdapter: OtherPropertiesAdapter) {
        val condition = Predicate {theProperty: PropertyEntity -> theProperty.id == property!!.id}
        otherProperties.removeIf(condition)
        otherPropertiesAdapter.setOtherProperties(otherProperties)
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    companion object {
        fun newInstance() = DetailsFragment()
    }
}