package com.example.loginconnav_saveargs.ui.fragments.splash

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.loginconnav_saveargs.auth.SessionManager
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashViewModel : ViewModel() {

    fun checkAuthStatus(): LiveData<Boolean> {
        val result = MutableLiveData<Boolean>()

        viewModelScope.launch {
            delay(4000)
            result.postValue(SessionManager.isLoggedIn())
        }

        return result
    }

    fun getUsername(): String {
        return SessionManager.getUsername()
    }
}