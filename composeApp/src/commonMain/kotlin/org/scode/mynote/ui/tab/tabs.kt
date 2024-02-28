package org.scode.mynote.ui.tab

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.NetworkCell
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import cafe.adriel.voyager.transitions.SlideTransition
import org.scode.mynote.ui.screen.history.HistoryScreen
import org.scode.mynote.ui.screen.net.NetScreen
import org.scode.mynote.ui.screen.setting.SettingScreen

object HistoryTab : Tab {
    @Composable
    override fun Content() {
        Navigator(screen = HistoryScreen()) {
            SlideTransition(it){
                it.Content()
            }
        }
    }

    override val options: TabOptions
        @Composable
        get() {
            val title = "记录"
            val icon = rememberVectorPainter(Icons.Default.List)
            return remember {
                TabOptions(
                    index = 0u,
                    title = title,
                    icon = icon
                )
            }
        }
}



object NetTab : Tab {
    @Composable
    override fun Content() {
        Navigator(screen = NetScreen()) {
            SlideTransition(it){
                it.Content()
            }
        }
    }

    override val options: TabOptions
        @Composable
        get() {
            val title = "网络"
            val icon = rememberVectorPainter(Icons.Default.NetworkCell)
            return remember {
                TabOptions(
                    index = 0u,
                    title = title,
                    icon = icon
                )
            }
        }
}


object SettingTab : Tab {
    @Composable
    override fun Content() {
        Navigator(screen = SettingScreen()) {
            SlideTransition(it){
                it.Content()
            }
        }
    }

    override val options: TabOptions
        @Composable
        get() {
            val title = "设置"
            val icon = rememberVectorPainter(Icons.Default.Settings)
            return remember {
                TabOptions(
                    index = 0u,
                    title = title,
                    icon = icon
                )
            }
        }
}