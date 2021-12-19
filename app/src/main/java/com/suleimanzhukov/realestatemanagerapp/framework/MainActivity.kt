package com.suleimanzhukov.realestatemanagerapp.framework

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.suleimanzhukov.realestatemanagerapp.R
import com.suleimanzhukov.realestatemanagerapp.framework.ui.MainFragment

class MainActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        auth = Firebase.auth

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container_fragment_main, MainFragment.newInstance())
                .commitAllowingStateLoss()
        }
    }

    override fun onStart() {
        super.onStart()
        val currentUser = auth.currentUser
    }

    fun getAuth() = auth
}