package com.gustavogoma.android.darkmodetest

import android.app.Application

class MyApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        setupDarkMode()
    }

    private fun setupDarkMode() {
        if (Settings.isDarkModeAvailable) {
            val darkMode = Settings.getDarkModePreference(this)
            Settings.setDarkModePreference(this, darkMode)
        }
    }

}