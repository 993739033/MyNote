package org.company.app.utils

import com.russhwolf.settings.NSUserDefaultsSettings
import com.russhwolf.settings.Settings

actual fun createSettings(): Settings {
    return NSUserDefaultsSettings(platform.Foundation.NSUserDefaults())
}