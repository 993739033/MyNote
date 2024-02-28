package daniel.avila.rnm.kmm.presentation.ui.common.state

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.painterResource

@Composable
fun Empty(
    modifier: Modifier = Modifier,
    msg: String = "Empty",
    onCheckAgain: () -> Unit = {}
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
//            Text(
//                text = msg,
//                style = MaterialTheme.typography.h5
//            )
            Image(
                painter = painterResource("drawable/no_data_1.png"),
                modifier = Modifier.size(width = 180.dp, height = 150.dp),
                contentDescription = "no_data"
            )
            Spacer(modifier = Modifier.size(10.dp))
            OutlinedButton(
                onClick = onCheckAgain
            ) {
                Text(
                    text = "Check Again",
                    style = MaterialTheme.typography.h6
                )
            }
        }
    }
}