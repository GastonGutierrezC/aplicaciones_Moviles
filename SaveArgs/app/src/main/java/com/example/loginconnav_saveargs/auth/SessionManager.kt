package com.example.loginconnav_saveargs.auth

import android.content.Context
import android.content.SharedPreferences

object SessionManager {
    private lateinit var prefs: SharedPreferences

    fun init(context: Context) {
        prefs = context.getSharedPreferences("LoginPrefs", Context.MODE_PRIVATE)
    }

    fun setLoggedIn(isLoggedIn: Boolean) {
        prefs.edit().putBoolean("is_logged_in", isLoggedIn).apply()
    }

    fun isLoggedIn(): Boolean {
        return prefs.getBoolean("is_logged_in", false)
    }

    fun saveUsername(username: String) {
        prefs.edit().putString("username", username).apply()
    }

    fun getUsername(): String {
        return prefs.getString("username", "") ?: ""
    }

    fun clearSession() {
        prefs.edit().clear().apply()
    }
}