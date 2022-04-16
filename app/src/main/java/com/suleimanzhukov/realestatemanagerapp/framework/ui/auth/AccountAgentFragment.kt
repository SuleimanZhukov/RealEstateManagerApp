package com.suleimanzhukov.realestatemanagerapp.framework.ui.auth

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.ImageView
import android.widget.PopupMenu
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import coil.load
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.SetOptions
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
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
import kotlinx.coroutines.tasks.await
import java.io.*
import javax.inject.Inject

class AccountAgentFragment : Fragment() {

    private var _binding: FragmentAccountAgentBinding? = null
    private val binding get() = _binding!!

    private lateinit var mainActivity: MainActivity
    private lateinit var navController: NavController

    @Inject
    lateinit var viewModel: AuthViewModel

    private lateinit var agent: AgentEntity
    private var email: String? = null

    private lateinit var profilePicture: Uri

    private lateinit var auth: FirebaseAuth
    private var db = Firebase.firestore

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
        auth = FirebaseAuth.getInstance()

        setHasOptionsMenu(true)

        setProfile()

//        email = getEmail()
//        setProfile(profileImage)

        subscribeToLiveData()

        profileImage.setOnClickListener {
            checkPermissions()
        }

        onAddPropertyButtonPress()
        backButtonPress()
    }

    private fun setProfile() = with(binding) {
        CoroutineScope(Main).launch {
            db.document("users/${auth.currentUser?.email}")
                .get()
                .addOnSuccessListener {
                    if (it.exists()) {
                        Log.d("TAG", "setProfile: YEAH, IT EXISTS")
                        if (it.getString("name") != null || it.getString("profileImg") != null) {
                            agent = AgentEntity(0, it.getString("name"), it.getLong("age"),
                                it.getString("email"), it.getString("tel"), it.getString("profileImg"),
                                it.getString("overview"), it.getLong("forSale"))
                        }
                    }
                }.addOnFailureListener {
                    Toast.makeText(context, "Failed to retrieve data", Toast.LENGTH_SHORT).show()
                }.await()

            profileAgentNameTextView.text = agent.username
            greyAreaForSaleNumberTextView.text = agent.forSale.toString()

            val storagePath = FirebaseStorage.getInstance()
                .reference
                .child(agent.profileImg.toString())

            storagePath.downloadUrl.addOnSuccessListener {
                profileImage.load(it)
            }
        }

        setMenu()
    }

    private fun setMenu() = with(binding) {
        profileOptionsButtonView.setOnClickListener {
            val popupMenu = PopupMenu(context, profileOptionsButtonView)
            val inflater = popupMenu.menuInflater
            inflater.inflate(R.menu.profile_menu, popupMenu.menu)
            popupMenu.show()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.profile_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.edit_profile -> {
                true
            }
            R.id.logout -> {
                logoutAgentByEmail()
                true
            }
            else -> {
                return super.onOptionsItemSelected(item)
            }
        }
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

            val inputStream = mainActivity.contentResolver.openInputStream(it.data?.data!!)

            val imageBitmap = BitmapFactory.decodeStream(inputStream)

            val outStream = ByteArrayOutputStream()
            val storagePath = FirebaseStorage.getInstance()
                .reference
                .child("${auth.currentUser?.email}/${auth.currentUser?.email}")

            imageBitmap.compress(Bitmap.CompressFormat.JPEG, 100, outStream)
            val byteArray = outStream.toByteArray()

            val upload = storagePath.putBytes(byteArray)

            upload.addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    storagePath.downloadUrl.addOnCompleteListener { image ->
                        if (image.isSuccessful) {
                            profilePicture = image.result

                            val imageData = hashMapOf(
                                "profileImg" to profilePicture.toString()
                            )

                            db.document("users/${auth.currentUser?.email}")
                                .set(imageData, SetOptions.merge())

                            binding.profileImage.load(profilePicture)
                        }
                    }
                    Toast.makeText(context, "Uploaded successfully", Toast.LENGTH_SHORT).show()
                }
            }.addOnFailureListener {
                Toast.makeText(context, "Failed to upload the image", Toast.LENGTH_SHORT).show()
            }

            /*CoroutineScope(Main).launch {
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
            }*/
        }
    }

    private fun getEmail(): String? {
        val preferences = activity?.getSharedPreferences(SignUpFragment.SHARED_TAG, Context.MODE_PRIVATE)
        return preferences?.getString(SignUpFragment.EMAIL_TAG, "")
    }

    @SuppressLint("CommitPrefEdits")
    private fun logoutAgentByEmail() {
        /*val preferencesEditor = activity?.getSharedPreferences(SignUpFragment.SHARED_TAG, Context.MODE_PRIVATE)?.edit()
        preferencesEditor?.remove(SignUpFragment.USERNAME_TAG)
        preferencesEditor?.remove(SignUpFragment.EMAIL_TAG)
        preferencesEditor?.apply()*/

        auth.signOut()
        navController.navigate(R.id.action_accountAgentFragment_to_mainFragment)
    }

    private fun subscribeToLiveData() {
//        viewModel.getAgentLiveData().observe(viewLifecycleOwner, Observer {
//            agent = viewModel.getAgentLiveData().value
//        })
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