package com.example.loginconnav_saveargs.ui.fragments.dashboard

import androidx.lifecycle.ViewModel
import com.example.loginconnav_saveargs.auth.SessionManager

class DashboardViewModel : ViewModel() {
    fun logout() {
        SessionManager.clearSession()
    }
}