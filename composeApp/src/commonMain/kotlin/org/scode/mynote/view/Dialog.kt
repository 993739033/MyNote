package org.scode.mynote.view

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import org.scode.mynote.config.Config
import org.scode.mynote.config.ThemeType
import org.scode.mynote.theme.*
import org.scode.mynote.theme.bg_light

@Composable
fun LoadingDialog(modifier: Modifier = Modifier) {
    Dialog(onDismissRequest = { /*showDialog = false*/ }) {
        Card(
            modifier = modifier
                .size(width = 180.dp, height = 200.dp)
                .alpha(0.9f)
        ) {
            Box(contentAlignment = Alignment.Center) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    CircularProgressIndicator(modifier = Modifier.padding(5.dp))
                    Text(
                        text = "LOADING....",
                        fontSize = 15.sp,
                        fontStyle = MaterialTheme.typography.h4.fontStyle
                    )
                }
            }
        }
    }
}


@OptIn(ExperimentalComposeUiApi::class)
@Composable
internal fun ToastDialog(modifier: Modifier = Modifier, data: String = "") {
    Dialog(
        onDismissRequest = { /*showDialog = false*/ },
    ) {
        Card(
            modifier = modifier
                .size(width = 180.dp, height = 150.dp)
        ) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier.background(Color.White)
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = "$data",
                        fontSize = 15.sp,
                        color = Color.Black,
                        fontStyle = MaterialTheme.typography.h1.fontStyle,
                    )
                }
            }
        }
    }
}


@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun ThemeDialog(
    modifier: Modifier = Modifier,
    showDialog: MutableState<Boolean>
) {
    Dialog(
        onDismissRequest = { showDialog.value = false },
    ) {
        Card(
            modifier = modifier
                .width(200.dp)
                .wrapContentHeight()
                .background(color = bg_light)
                .padding(5.dp)
        ) {
            LazyColumn(modifier = Modifier.fillMaxWidth(1f)) {
                item {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier
                            .fillMaxWidth(1f)
                            .padding(5.dp)
                            .background(color = Color.White)
                            .clickable() {
                                Config._themeConfig.value = ThemeType.PURPLE
                            }
                    ) {
                        Text(text = "Purple")
                        Spacer(
                            modifier = Modifier
                                .background(color = purple)
                                .size(40.dp)
                        )
                    }
                }

                item {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier
                            .fillMaxWidth(1f)
                            .padding(5.dp)
                            .background(color = Color.White)
                            .clickable() {
                                Config._themeConfig.value = ThemeType.GREEN
                            }
                    ) {
                        Text(text = "Green")
                        Spacer(
                            modifier = Modifier
                                .background(color = green500)
                                .size(40.dp)
                        )
                    }
                }

                item {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier
                            .fillMaxWidth(1f)
                            .padding(5.dp)
                            .background(color = Color.White)
                            .clickable() {
                                Config._themeConfig.value = ThemeType.BLUE
                            }
                    ) {
                        Text(text = "Blue")
                        Spacer(
                            modifier = Modifier
                                .background(color = blue500)
                                .size(40.dp)
                        )
                    }
                }

                item {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier
                            .fillMaxWidth(1f)
                            .padding(5.dp)
                            .background(color = Color.White)
                            .clickable() {
                                Config._themeConfig.value = ThemeType.ORANGE
                            }
                    ) {
                        Text(text = "Orange")
                        Spacer(
                            modifier = Modifier
                                .background(color = orange500)
                                .size(40.dp)
                        )
                    }
                }
            }
        }
    }
}