package org.scode.mynote.ui.screen.main

import androidx.compose.foundation.layout.RowScope
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.currentOrThrow
import cafe.adriel.voyager.navigator.tab.CurrentTab
import cafe.adriel.voyager.navigator.tab.LocalTabNavigator
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabNavigator
import cafe.adriel.voyager.transitions.SlideTransition
import org.company.app.utils.Log
import org.scode.mynote.base.mvi.BaseScreen
import org.scode.mynote.ui.event.GobalEvent
import org.scode.mynote.ui.screen.detail.DetailScreen
import org.scode.mynote.ui.tab.HistoryTab
import org.scode.mynote.ui.tab.NetTab
import org.scode.mynote.ui.tab.SettingTab

@Composable
fun AppScreen() {
    Navigator(screen = MainScreen()) {
        SlideTransition(it) {
            it.Content()
        }
    }
}

class MainScreen : BaseScreen() {
    @OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
    @Composable
    override fun CreateContent(nav: Navigator) {
        val windowClass = calculateWindowSizeClass()
        TabNavigator(tab = HistoryTab) {
            Scaffold(
                topBar = {
                    TopAppBar(title = { Text(text = it.current.options.title) }, actions = {
                        if (it.current == HistoryTab) {
                            IconButton(onClick = {
                                if (it.current == HistoryTab) {
                                    nav.push(DetailScreen())
                                }
                            }) {
                                Icon(imageVector = Icons.Default.Add, contentDescription = "add")
                            }
                        }
                    })
                },
                content = {
                    CurrentTab()
                },
                bottomBar = {
                    BottomNavigation {
                        TabNavigationItem(HistoryTab)
                        TabNavigationItem(NetTab)
                        TabNavigationItem(SettingTab)
                    }
                }
            )
        }
    }

    override fun onGobalEvent(event: GobalEvent, nav: Navigator) {
        when (event) {
            is GobalEvent.EditNote -> {
                Log.e("to edit")
                nav.push(DetailScreen(event.id))
            }
        }
    }
}


@Composable
fun RowScope.TabNavigationItem(tab: Tab) {
    val tabNavigator = LocalTabNavigator.current

    BottomNavigationItem(
        selected = tabNavigator.current.key == tab.key,
        onClick = { tabNavigator.current = tab },
        icon = {
            Icon(
                painter = tab.options.icon!!,
                contentDescription = tab.options.title
            )
        }
    )
}