package com.suleimanzhukov.realestatemanagerapp.framework.ui.auth

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.SetOptions
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.suleimanzhukov.realestatemanagerapp.R
import com.suleimanzhukov.realestatemanagerapp.RealEstateApplication
import com.suleimanzhukov.realestatemanagerapp.databinding.FragmentPublishBinding
import com.suleimanzhukov.realestatemanagerapp.framework.MainActivity
import com.suleimanzhukov.realestatemanagerapp.framework.ui.adapters.PublishPicturesAdapter
import com.suleimanzhukov.realestatemanagerapp.model.database.entities.PictureEntity
import com.suleimanzhukov.realestatemanagerapp.model.database.entities.PropertyEntity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import javax.inject.Inject
import java.text.SimpleDateFormat
import java.util.*


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

    private var auth = FirebaseAuth.getInstance()
    private var db = Firebase.firestore

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
//        val sdf = SimpleDateFormat("dd/M/yyyy hh:mm:ss")
//        val currentTime = sdf.format(Date()).toString().toLong()

        publishButton.setOnClickListener {
            val id = 0L
            val publisher = auth.currentUser?.email!!
            val publisherUsername = ""
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

            val property = PropertyEntity(id, publisherUsername, publisher, price, address,
                type, timePublished, beds, baths, garages, area, details, location)

            db.document("users/${auth.currentUser?.email}")
                .collection("properties")
                .add(property)
                .addOnSuccessListener {
                    Toast.makeText(context, "Successfully uploaded", Toast.LENGTH_SHORT).show()
                    db.document("users/${auth.currentUser?.email}")
                        .collection("properties")
                        .whereEqualTo("publisher", "suleimanzhukov@gmail.com")
                        .get()
                        .addOnSuccessListener {
                            Toast.makeText(context, "YEAH, IT HEREEEEE", Toast.LENGTH_SHORT).show()
                        }.addOnFailureListener {
                            Toast.makeText(context, "NOOOOOOO", Toast.LENGTH_SHORT).show()
                        }
                }.addOnFailureListener {
                    Toast.makeText(context, "Failed to upload", Toast.LENGTH_SHORT).show()
                }

            navController.navigate(R.id.action_publishFragment_to_mainFragment)

            /*CoroutineScope(Main).launch {
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

                pictures.forEach {
                    it.propertyId = id
                }

                val getLastJob = async(IO) {
                    viewModel.addPictures(pictures)
                }
                getLastJob.await()
            }*/
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
            picture = PictureEntity(0, 0, it.data?.data.toString(), 1)
            pictures.add(picture)
            publishPicturesAdapter.addPublishPictures(picture)
         /*   CoroutineScope(Main).launch {

                val getPictureEntityJob = async(IO) {
                    viewModel.addPicture(PictureEntity(
                        0,
                        0,
                        it.data?.data.toString(),
                        1
                    ))
                }
                getPictureEntityJob.await()

                val addPictureJob = async(IO) {
                    viewModel.addPicture(picture)
                }
                addPictureJob.await()
            }*/
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