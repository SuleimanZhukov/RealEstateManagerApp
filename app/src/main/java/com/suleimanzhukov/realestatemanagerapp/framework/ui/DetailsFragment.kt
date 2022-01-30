package com.suleimanzhukov.realestatemanagerapp.framework.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.suleimanzhukov.realestatemanagerapp.databinding.FragmentDetailsBinding
import com.suleimanzhukov.realestatemanagerapp.framework.MainActivity

class DetailsFragment : Fragment() {

    private var _binding: FragmentDetailsBinding? = null
    private val binding get() = _binding!!

    private lateinit var mainActivity: MainActivity
    private lateinit var navController: NavController

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mainActivity = activity as MainActivity
        navController = Navigation.findNavController(view)


        backButtonPress()
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