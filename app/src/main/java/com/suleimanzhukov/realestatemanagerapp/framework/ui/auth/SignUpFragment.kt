package com.suleimanzhukov.realestatemanagerapp.framework.ui.auth

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.suleimanzhukov.realestatemanagerapp.R
import com.suleimanzhukov.realestatemanagerapp.RealEstateApplication
import com.suleimanzhukov.realestatemanagerapp.databinding.FragmentSignUpBinding
import javax.inject.Inject

class SignUpFragment : Fragment() {

    private var _binding: FragmentSignUpBinding? = null
    private val binding get() = _binding!!

    private lateinit var navController: NavController

    @Inject
    lateinit var viewModel: AuthViewModel

    private lateinit var auth: FirebaseAuth
    private var db = Firebase.firestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        RealEstateApplication.instance.appComponent.inject(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentSignUpBinding.inflate(inflater, container, false)
        return binding.root
    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) = with(binding) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)
        auth = FirebaseAuth.getInstance()

        signUpButton.setOnClickListener {
            signUpButton(
                signUpNameEditText.text.toString(),
                signUpEmailEditText.text.toString(),
                signUpPasswordEditText.text.toString(),
                signUpConfirmPasswordEditText.text.toString()
            )
        }
    }

    @SuppressLint("CommitPrefEdits")
    private fun signUpButton(username: String, email: String, password: String, confirmPassword: String): Unit = with(binding) {
        if (username.isBlank()) {
            Toast.makeText(context, "Insert your name", Toast.LENGTH_SHORT).show()
            return
        }
        if (!email.contains('@') || !email.contains('.') || email.isBlank()) {
            Toast.makeText(context, "Email incorrect", Toast.LENGTH_SHORT).show()
            return
        }

        if (password != confirmPassword || password.isBlank() || confirmPassword.isBlank()) {
            Toast.makeText(context, "Confirm password", Toast.LENGTH_SHORT).show()
            return
        }

        val user = hashMapOf(
            "name" to username,
            "email" to email,
            "age" to 0,
            "profileImg" to "",
            "overview" to "",
            "tel" to "",
            "forSale" to 0
        )

        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener {
            if (it.isSuccessful) {
                db.document("users/$email")
                    .set(user)
                    .addOnSuccessListener {
                        Toast.makeText(context, "Successfully added to Firestore", Toast.LENGTH_SHORT).show()
                    }.addOnFailureListener {
                        Toast.makeText(context, "Failed to add to Firestore", Toast.LENGTH_SHORT).show()
                    }
                Toast.makeText(context, "Successfully registered", Toast.LENGTH_SHORT).show()
                navController.navigate(R.id.action_signUpFragment_to_accountAgentFragment)
            }
        }.addOnFailureListener {
            Toast.makeText(context, "Registration failed", Toast.LENGTH_SHORT).show()
        }

        /*val agent = AgentEntity(0, username, "", email, password, "",  "",
        "", "", "", 0, 0)

        val bundle = Bundle().apply {
            putParcelable(AccountAgentFragment.AGENT_KEY, agent)
        }

        CoroutineScope(Main).launch {
            val job = async(IO) {
                viewModel.registerAgent(agent)
            }
            job.await()
        }

        val preferencesEditor = activity?.getSharedPreferences(SHARED_TAG, Context.MODE_PRIVATE)?.edit()
        preferencesEditor?.putString(USERNAME_TAG, username)
        preferencesEditor?.putString(EMAIL_TAG, email)
        preferencesEditor?.apply()

        navController.navigate(R.id.action_signUpFragment_to_accountAgentFragment)*/
    }


    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    companion object {
        const val SHARED_TAG = "shared_tag"
        const val EMAIL_TAG = "email_tag"
        const val USERNAME_TAG = "user_tag"

        fun newInstance() = SignUpFragment()
    }
}