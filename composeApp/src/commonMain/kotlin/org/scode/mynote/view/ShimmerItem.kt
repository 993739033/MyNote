package org.scode.mynote.view

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.TileMode
import androidx.compose.ui.unit.dp


@Composable
fun LoadingView() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        val infinite = rememberInfiniteTransition()

        val translateAnim by infinite.animateFloat(
            initialValue = 100f, targetValue = 800f, animationSpec = infiniteRepeatable(
                tween(durationMillis = 3000, easing = LinearEasing), RepeatMode.Restart
            )
        )
        ShimmerItem(floatAnim = translateAnim)
        ShimmerItem(floatAnim = translateAnim)
        ShimmerItem(floatAnim = translateAnim)
        ShimmerItem(floatAnim = translateAnim)
        ShimmerItem(floatAnim = translateAnim)
    }
}


@Composable
fun ShimmerItem(
    colors: List<Color> = listOf(
        Color.LightGray.copy(alpha = 0.6f), Color.LightGray
    ), floatAnim: Float = 0f
) {
    val brush = Brush.linearGradient(
        colors,
        start = Offset(0f, 0f),
        end = Offset(floatAnim, floatAnim),
        tileMode = TileMode.Clamp
    )

    Row(modifier = Modifier.padding(10.dp)) {
        Spacer(
            modifier = Modifier
                .size(100.dp)
                .background(brush = brush)
        )

        Column(modifier = Modifier.padding(8.dp)) {
            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(30.dp)
                    .padding(8.dp)
                    .background(brush)
            )
            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(30.dp)
                    .padding(8.dp)
                    .background(brush)
            )
            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(30.dp)
                    .padding(8.dp)
                    .background(brush)
            )
        }
    }
}