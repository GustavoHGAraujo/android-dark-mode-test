package com.gustavogoma.android.darkmodetest

import android.content.Context
import android.content.SharedPreferences
import android.os.Build
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.app.AppCompatDelegate.NightMode
import com.gustavogoma.android.darkmodetest.R

object Settings {

    // Shared Preferences (sp)
    private const val SP_CURRENT_USER_ID = "sp_current_user_id"
    private const val SP_DARK_MODE = "sp_dark_mode"
    private const val SP_FIRST_RUN = "sp_first_run"

    enum class DarkMode(
        @StringRes val text: Int,
        @NightMode val nightMode: Int,
        val minVersion: Int
    ) {
        ALWAYS_DISABLED(
            R.string.dark_mode_no,
            AppCompatDelegate.MODE_NIGHT_NO,
            Build.VERSION_CODES.JELLY_BEAN
        ),
        ALWAYS_ENABLED(
            R.string.dark_mode_yes,
            AppCompatDelegate.MODE_NIGHT_YES,
            Build.VERSION_CODES.JELLY_BEAN
        ),
        FOLLOW_BATTERY_MODE(
            R.string.dark_mode_battery_mode,
            AppCompatDelegate.MODE_NIGHT_AUTO_BATTERY,
            Build.VERSION_CODES.LOLLIPOP
        ),
        FOLLOW_SYSTEM(
            R.string.dark_mode_follow_system,
            AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM,
            Build.VERSION_CODES.Q
//        ),
//        UNSPECIFIED(
//            R.string.dark_mode_unspecified,
//            AppCompatDelegate.MODE_NIGHT_UNSPECIFIED,
//            Build.VERSION_CODES.P
        )
    }

    @JvmStatic
    val isDarkModeAvailable: Boolean
        get() = Build.VERSION.SDK_INT >= DarkMode.values()
            .map { it.minVersion }
            .min()!!


    private fun getSharedPreferences(context: Context): SharedPreferences {
        val preferencesFileKey = context.getString(R.string.preference_file_key)
        return context.getSharedPreferences(preferencesFileKey, Context.MODE_PRIVATE)
    }

    private fun getDarkModeFromNightMode(@NightMode nightMode: Int) =
        DarkMode.values().firstOrNull { it.nightMode == nightMode }


    @JvmStatic
    fun getAvailableDarkModes() =
        DarkMode.values().filter { it.minVersion <= Build.VERSION.SDK_INT }

    @JvmStatic
    fun getDarkModePreference(context: Context): DarkMode {
        val sharedPref = getSharedPreferences(context)
        val fallbackDarkMode = getFallbackDarkModeTheme()
        val nightMode = sharedPref.getInt(SP_DARK_MODE, fallbackDarkMode.nightMode)

        return getDarkModeFromNightMode(nightMode) ?: fallbackDarkMode
    }

    @JvmStatic
    fun getFallbackDarkModeTheme() = when {
        Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q -> DarkMode.FOLLOW_SYSTEM
        Build.VERSION.SDK_INT >= Build.VERSION_CODES.P -> DarkMode.FOLLOW_BATTERY_MODE
        else -> DarkMode.ALWAYS_DISABLED
    }

    @JvmStatic
    fun setDarkModePreference(context: Context, darkMode: DarkMode) {
        val sharedPref = getSharedPreferences(context)

        with(sharedPref.edit()) {
            putInt(SP_DARK_MODE, darkMode.nightMode)
            apply()
        }

        AppCompatDelegate.setDefaultNightMode(darkMode.nightMode)
    }
}