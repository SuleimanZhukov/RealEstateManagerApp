package com.suleimanzhukov.realestatemanagerapp.framework.ui.signup

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.suleimanzhukov.realestatemanagerapp.AppState
import com.suleimanzhukov.realestatemanagerapp.model.repository.RepositoryImpl

class SignUpViewModel(
    private val repository: RepositoryImpl
) : ViewModel() {

    private val signUpLiveData: MutableLiveData<AppState> = MutableLiveData()
}