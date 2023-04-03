package com.example.chatkgb

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate

class Chat:Application() {
    override fun onCreate() {
        super.onCreate()
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
    }
}