package org.company.app.utils

import android.preference.PreferenceManager
import com.russhwolf.settings.Settings
import com.russhwolf.settings.SharedPreferencesSettings
import org.scode.mynote.AndroidApp

actual fun createSettings(): Settings {
    return SharedPreferencesSettings(PreferenceManager.getDefaultSharedPreferences(AndroidApp.INSTANCE))
}