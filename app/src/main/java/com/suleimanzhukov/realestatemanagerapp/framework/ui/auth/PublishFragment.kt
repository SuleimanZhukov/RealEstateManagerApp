package com.suleimanzhukov.realestatemanagerapp.framework.ui.auth

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.suleimanzhukov.realestatemanagerapp.R
import com.suleimanzhukov.realestatemanagerapp.RealEstateApplication
import com.suleimanzhukov.realestatemanagerapp.databinding.FragmentPublishBinding
import com.suleimanzhukov.realestatemanagerapp.framework.MainActivity
import com.suleimanzhukov.realestatemanagerapp.framework.ui.adapters.PublishPicturesAdapter
import com.suleimanzhukov.realestatemanagerapp.model.database.entities.PictureEntity
import com.suleimanzhukov.realestatemanagerapp.model.database.entities.PropertyEntity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.system.measureNanoTime

class PublishFragment : Fragment() {

    private var _binding: FragmentPublishBinding? = null
    private val binding get() = _binding!!

    private lateinit var mainActivity: MainActivity
    private lateinit var navController: NavController

    private lateinit var publishPicturesAdapter: PublishPicturesAdapter
    private lateinit var picture: PictureEntity
    private var pictures = mutableListOf<PictureEntity>()

    @Inject
    lateinit var viewModel: AuthViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        RealEstateApplication.instance.appComponent.inject(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentPublishBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mainActivity = activity as MainActivity
        navController = Navigation.findNavController(view)

        subscribeToLiveData()
        backButtonPress()
        setPublishPicturesAdapter()
        onAddImageButton()
        onPublish()
    }

    private fun onPublish() = with(binding) {
        publishButton.setOnClickListener {
            val publisher = getEmail()!!
            val publisherUsername = getUsername()!!
            val price = priceInputEditText.text.toString().toInt()
            val address = "${provinceInputEditText.text.toString()}, ${cityInputEditText.text.toString()}," +
                    " ${firstAddressInputEditText.text.toString()}, ${secondAddressInputEditText.text.toString()}"
            val type = typeInputEditText.text.toString()
            val timePublished = ""
            val beds = bedsInputEditText.text.toString().toInt()
            val baths = bathsInputEditText.text.toString().toInt()
            val garages = garagesInputEditText.text.toString().toInt()
            val details = aboutEditText.text.toString()
            val area = squareInputEditText.text.toString().toInt()
            val location = ""

            val property = PropertyEntity(0, publisherUsername, publisher, price, address,
                type, timePublished, beds, baths, garages, area, details, location)

            var properties: List<PropertyEntity?> = mutableListOf()

            CoroutineScope(Main).launch {
                val job = async(IO) {
                    viewModel.addProperty(property)
                }
                job.await()
                navController.navigate(R.id.action_publishFragment_to_mainFragment)

                val getPropertiesJob = async(IO) {
                    properties = viewModel.getAllProperties()
                }
                getPropertiesJob.await()

                val id = properties[properties.lastIndex]!!.id
                Log.d("TAG", "onPublish: PUBLISHING... Id: $id")
                pictures.forEach {
                    it.propertyId = id
                }

                Log.d("TAG", "onPublish: Picture Property Id: ${pictures[0].propertyId}")

                val getLastJob = async(IO) {
                    pictures.forEach {
                        viewModel.addPictures(pictures)
                    }
                }
                getLastJob.await()

                var picturitos = listOf<PictureEntity>()

                val getAllJob = async(IO) {
                    picturitos = viewModel.getAllPictures()
                }
                getAllJob.await()

                Log.d("TAG", "onPublish: Picture Property Id: ${picturitos[0].propertyId}")
            }
        }
    }

    private fun onAddImageButton() = with(binding) {
        publishAddImageButton.setOnClickListener {
            checkPermissions()
        }
    }

    private fun setPublishPicturesAdapter() = with(binding) {
        publishPicturesAdapter = PublishPicturesAdapter(requireContext())
        publishImagesRecyclerView.adapter = publishPicturesAdapter
        publishImagesRecyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
    }

    private fun checkPermissions() {
        if (mainActivity.checkReadStoragePermission()) {
            uploadImage()
        } else {
            mainActivity.requestReadStoragePermission()
        }
    }

    private fun uploadImage() {
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
                picture = PictureEntity(0, 0, it.data?.data.toString(), 1)
                pictures.add(picture)
                publishPicturesAdapter.addPublishPictures(picture)
//                val getPictureEntityJob = async(IO) {
//                    viewModel.addPicture(PictureEntity(
//                        0,
//                        0,
//                        it.data?.data.toString(),
//                        1
//                    ))
//                }
//                getPictureEntityJob.await()
//
//                val addPictureJob = async(IO) {
//                    viewModel.addPicture(picture)
//                }
//                addPictureJob.await()
            }
        }
    }

    private fun getEmail(): String? {
        val preferencesEditor = activity?.getSharedPreferences(SignUpFragment.SHARED_TAG, Context.MODE_PRIVATE)
        return preferencesEditor?.getString(SignUpFragment.EMAIL_TAG, "")
    }

    private fun getUsername(): String? {
        val preferencesEditor = activity?.getSharedPreferences(SignUpFragment.SHARED_TAG, Context.MODE_PRIVATE)
        return preferencesEditor?.getString(SignUpFragment.USERNAME_TAG, "")
    }

    private fun subscribeToLiveData() = with(binding) {
        viewModel.getPictureLiveData().observe(viewLifecycleOwner, Observer {
            picture = viewModel.getPictureLiveData().value!!
        })
    }

    private fun backButtonPress() = with(binding) {
        publishArrowBack.setOnClickListener {
            mainActivity.onBackPressed()
        }
        publishBackButtonView.setOnClickListener {
            mainActivity.onBackPressed()
        }
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}