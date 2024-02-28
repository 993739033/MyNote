package daniel.avila.rnm.kmm.presentation.ui.common.state

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.company.app.utils.Log
import org.scode.mynote.base.state.ResourceUiState
import org.scode.mynote.view.LoadingDialog

@Composable
fun <T> ManagementResourceUiState(
    modifier: Modifier = Modifier,
    resourceUiState: ResourceUiState<T>,
    onTryAgain: () -> Unit,
    successView: @Composable (data: T) -> Unit,
    loadingView: @Composable () -> Unit = {  },
    errorView: @Composable (errMsg: String?) -> Unit = {
        Error(
            modifier = modifier,
            onTryAgain = onTryAgain,
            msg = it ?: ""
        )
    },
    msgTryAgain: String = "No data to show",
    onCheckAgain: () -> Unit,
    msgCheckAgain: String = "NO DATA CHECK AGAIN!",
    needShowDialog: Boolean = false
) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center,
    ) {
        var showDialog by remember { mutableStateOf(false) }
        Log.e("uiState: ${resourceUiState.toString()}")
        when (resourceUiState) {
            is ResourceUiState.Empty -> Empty(modifier = modifier, onCheckAgain = onCheckAgain, msg = msgCheckAgain)
            is ResourceUiState.Error -> errorView(resourceUiState.message)
            ResourceUiState.Loading -> loadingView()
            is ResourceUiState.Success -> successView(resourceUiState.data)
            ResourceUiState.Idle -> Unit
        }

        rememberCoroutineScope().launch {
            if (resourceUiState == ResourceUiState.Loading) {
                showDialog = true
            } else {
                delay(800)
                showDialog = false
            }
        }

        if (showDialog && needShowDialog) {
            LoadingDialog(modifier = Modifier.align(Alignment.Center))
        }
    }
}