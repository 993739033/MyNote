package org.company.app.utils

import com.russhwolf.settings.Settings
import com.russhwolf.settings.get

object SettingUtil {
    val instance = createSettings()

    fun put(key: String, value: Any) {
        when (value) {
            is Int -> instance.putInt(key, value)
            is String -> instance.putString(key, value)
            is Long -> instance.putLong(key, value)
            is Float -> instance.putFloat(key, value)
            is Double -> instance.putDouble(key, value)
            is Boolean -> instance.putBoolean(key, value)
        }
    }

    fun get(key: String, tempValue: Any): Any {
        val value = when (tempValue) {
            is Int -> return instance.get(key, tempValue)
            is String -> return instance.get(key, tempValue)
            is Long -> return instance.get(key, tempValue)
            is Float -> return instance.get(key, tempValue)
            is Boolean -> return instance.get(key, tempValue)
            is Double -> return instance.get(key, tempValue)
            else -> {
                tempValue
            }
        }
        return value
    }
}

expect fun createSettings(): Settings


//存储键
object SettingKey {
    val themeType = "themeType";
    val isDark = "isDark";
}
