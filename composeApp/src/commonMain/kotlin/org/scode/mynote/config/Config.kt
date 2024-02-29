package org.scode.mynote.config

import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import kotlinx.coroutines.flow.MutableStateFlow
import org.company.app.utils.SettingKey
import org.company.app.utils.SettingUtil

object Config {
    val _themeConfig = MutableStateFlow(ThemeType.PURPLE)
    val _isDark = MutableStateFlow(false)

    init {
        _themeConfig.value = when (SettingUtil.get(SettingKey.themeType, "purple")) {
            "purple" -> {
                ThemeType.PURPLE
            }

            "green" -> {
                ThemeType.GREEN
            }

            "orange" -> {
                ThemeType.ORANGE
            }

            else -> {
                ThemeType.BLUE
            }
        }

        _isDark.value = SettingUtil.get(SettingKey.isDark, false) as Boolean
    }

}

public enum class ThemeType {
    PURPLE, GREEN, ORANGE, BLUE
}