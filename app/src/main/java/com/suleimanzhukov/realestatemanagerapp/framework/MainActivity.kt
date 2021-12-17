package com.suleimanzhukov.realestatemanagerapp.framework

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.suleimanzhukov.realestatemanagerapp.R
import com.suleimanzhukov.realestatemanagerapp.framework.ui.MainFragment

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container_fragment_main, MainFragment.newInstance())
                .commitAllowingStateLoss()
        }
    }
}