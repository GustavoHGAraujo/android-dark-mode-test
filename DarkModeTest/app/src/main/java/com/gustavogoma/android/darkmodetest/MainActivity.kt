package com.gustavogoma.android.darkmodetest

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AppCompatDelegate
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        dark_mode_always_button.setOnClickListener { setAlwaysDark() }
        dark_mode_never_button.setOnClickListener { setNeverDark() }
        dark_mode_follow_battery_saving_mode_button.setOnClickListener { setDarkWhenInBatterySavingMode() }
        dark_mode_follow_theme_button.setOnClickListener { setDarkFollowingSystem() }
        dark_mode_unspecified_button.setOnClickListener { setDarkUnspecified() }

        android_version_text_view.text = "Android ${Build.VERSION.RELEASE}"
    }

    private fun setAlwaysDark() {
        setDarkMode(AppCompatDelegate.MODE_NIGHT_YES)
    }

    private fun setNeverDark() {
        setDarkMode(AppCompatDelegate.MODE_NIGHT_NO)
    }

    private fun setDarkWhenInBatterySavingMode() {
        setDarkMode(AppCompatDelegate.MODE_NIGHT_AUTO_BATTERY)
    }

    private fun setDarkFollowingSystem() {
        setDarkMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
    }

    private fun setDarkUnspecified() {
        setDarkMode(AppCompatDelegate.MODE_NIGHT_UNSPECIFIED)
    }

    private fun setDarkMode(@AppCompatDelegate.NightMode darkMode: Int) {
        AppCompatDelegate.setDefaultNightMode(darkMode)
    }
}
