package com.example.loginconnav_saveargs.ui.fragments.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.loginconnav_saveargs.auth.SessionManager
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

sealed class LoginResult {
    data class Success(val username: String) : LoginResult()
    data class Error(val message: String) : LoginResult()
}

class LoginViewModel : ViewModel() {
    private val _loginResult = MutableLiveData<LoginResult>()
    val loginResult: LiveData<LoginResult> = _loginResult

    fun login(email: String, password: String) {
        viewModelScope.launch {
            delay(1000)

            if (email.isNotEmpty() && password.isNotEmpty()) {
                val username = email.substringBefore("@")
                SessionManager.setLoggedIn(true)
                SessionManager.saveUsername(username)
                _loginResult.postValue(LoginResult.Success(username))
            } else {
                _loginResult.postValue(LoginResult.Error("Credenciales inválidas"))
            }
        }
    }
}