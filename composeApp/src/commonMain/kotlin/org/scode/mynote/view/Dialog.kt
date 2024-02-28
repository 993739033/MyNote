package org.scode.mynote.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties

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
fun ToastDialog(modifier: Modifier = Modifier, data: String = "") {
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