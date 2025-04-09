package com.example.loginconnav_saveargs.ui.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.loginconnav_saveargs.R
import com.example.loginconnav_saveargs.auth.SessionManager


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        SessionManager.init(applicationContext)
    }
}