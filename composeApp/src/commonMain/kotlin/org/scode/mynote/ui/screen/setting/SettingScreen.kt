package org.scode.mynote.ui.screen.setting

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import org.jetbrains.compose.resources.painterResource
import org.scode.mynote.config.Config
import org.scode.mynote.view.ThemeDialog

//设置界面
class SettingScreen : Screen {
    @Composable
    override fun Content() {
        val darkState = Config._isDark.collectAsState()
        //显示主题选项弹窗
        val showColorDialog = remember { mutableStateOf(false) }

        Box() {
            LazyColumn(
                modifier = Modifier
                    .background(MaterialTheme.colorScheme.background)
                    .fillMaxSize(1f)
            ) {
                item {
                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .fillMaxSize(1f)
                            .height(72.dp)
                            .padding(5.dp)
                            .shadow(3.dp, shape = RoundedCornerShape(10.dp), clip = true)
                            .background(MaterialTheme.colorScheme.surface)
                            .clickable {
                                Config._isDark.value = !Config._isDark.value
                            }
                    ) {
                        if (!darkState.value) {
                            Image(
                                painter = painterResource("drawable/icon_bt.png"),
                                modifier = Modifier
                                    .size(40.dp)
                                    .padding(start = 10.dp),
                                contentDescription = "day mode"
                            )
                            Text(
                                text = "白天模式", modifier = Modifier.padding(end = 10.dp),
                                fontSize = 20.sp, color = MaterialTheme.colorScheme.onSurface
                            )
                        } else {
                            Image(
                                painter = painterResource("drawable/icon_hy.png"),
                                modifier = Modifier
                                    .size(40.dp)
                                    .padding(start = 5.dp)
                                    .background(MaterialTheme.colorScheme.onSurface),
                                contentDescription = "day mode"
                            )
                            Text(
                                text = "黑夜模式",
                                fontSize = 20.sp,
                                modifier = Modifier.padding(end = 10.dp),
                                color = MaterialTheme.colorScheme.onSurface
                            )
                        }
                    }
                }

                item {
                    Row(
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .fillMaxSize(1f)
                            .height(72.dp)
                            .padding(5.dp)
                            .shadow(3.dp, shape = RoundedCornerShape(10.dp), clip = true)
                            .background(MaterialTheme.colorScheme.surface)
                            .clickable {
                                showColorDialog.value = true
                            }
                    ) {
                        Text(
                            text = "主题切换",
                            fontSize = 20.sp,
                            modifier = Modifier.padding(end = 10.dp),
                            color = MaterialTheme.colorScheme.onSurface
                        )
                    }
                }

            }
        }

        if (showColorDialog.value) {
            ThemeDialog(showDialog = showColorDialog)
        }
    }
}