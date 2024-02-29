package org.scode.mynote.ui.screen.history

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.Icon
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.koin.getScreenModel
import cafe.adriel.voyager.navigator.Navigator
import daniel.avila.rnm.kmm.presentation.ui.common.state.ManagementResourceUiState
import kotlinx.coroutines.flow.collectLatest
import org.scode.mynote.base.mvi.BaseScreen
import org.scode.mynote.base.state.ResourceUiState
import org.scode.mynote.ui.event.GobalEvent
import org.scode.mynote.view.LoadingView

//已保存数据
class HistoryScreen : BaseScreen() {

    @OptIn(ExperimentalMaterialApi::class)
    @Composable
    override fun CreateContent(nav: Navigator) {
        val model = getScreenModel<HistoryListModel>()

        val state by model.uiState.collectAsState()

        var scrollState = rememberLazyListState()

        val pullRefreshState = rememberPullRefreshState(
            refreshing = state.notes == ResourceUiState.Loading,
            onRefresh = {}
        )

        LaunchedEffect(Unit) {
            model.effect.collectLatest {
                when (it) {
                    is HistoryContract.Effect.NavigateToNoteDetail -> {
                        it.note.id?.let {
                            sendGobalEvent(GobalEvent.EditNote(it))
                        }
                    }
                }
            }
        }

        Box(
            modifier = Modifier
                .fillMaxHeight(1f)
                .padding(bottom = 50.dp)
                .background(Color.White)
                .pullRefresh(pullRefreshState)
        ) {
            ManagementResourceUiState(
                modifier = Modifier.fillMaxSize(1f),
                resourceUiState = state.notes,
                needShowDialog = false,
                loadingView = {
                    sendGobalEvent(event = GobalEvent.ShowToast("加载中.."))
                    LoadingView()
                },
                successView = {
                    LazyColumn(
                        state = scrollState,
                        modifier = Modifier
                            .fillMaxSize(1f)
                            .background(androidx.compose.material3.MaterialTheme.colorScheme.background)
                    ) {
                        it.forEach {
                            item {
                                Column(
                                    verticalArrangement = Arrangement.Center,
                                    modifier = Modifier
                                        .height(100.dp)
                                        .fillMaxWidth(1f)
                                        .padding(5.dp)
                                        .shadow(3.dp, shape = RoundedCornerShape(10.dp), clip = true)
                                        .background(androidx.compose.material3.MaterialTheme.colorScheme.surface)
                                        .clickable(onClick = {
                                            model.setEvent(HistoryContract.Event.onNoteClick(it))
                                        }),
                                ) {
                                    Row(verticalAlignment = Alignment.CenterVertically) {
                                        Text(
                                            modifier = Modifier.fillMaxWidth(0.5f),
                                            text = "Title:${it.name}",
                                            textAlign = TextAlign.Center,
                                            fontSize = 16.sp,
                                            color = androidx.compose.material3.MaterialTheme.colorScheme.onSurface,
                                            fontStyle = MaterialTheme.typography.h1.fontStyle
                                        )
                                        Text(
                                            modifier = Modifier.fillMaxWidth(1f),
                                            text = "Time:${it.lastTime}",
                                            textAlign = TextAlign.Center,
                                            color = androidx.compose.material3.MaterialTheme.colorScheme.onSurface,
                                            fontSize = 12.sp,
                                            fontStyle = MaterialTheme.typography.h3.fontStyle
                                        )
                                    }
                                }
                            }
                        }
                    }
                },
                onTryAgain = {
                    model.setEvent(HistoryContract.Event.refreshData)
                },
                onCheckAgain = {
                    model.setEvent(HistoryContract.Event.refreshData)
                }
            )

            PullRefreshIndicator(
                refreshing = state.notes == ResourceUiState.Loading,
                state = pullRefreshState,
                modifier = Modifier.align(Alignment.TopCenter),
                contentColor = androidx.compose.material3.MaterialTheme.colorScheme.primary
            )

            FloatingActionButton(modifier = Modifier
                .align(Alignment.BottomEnd)
                .size(70.dp)
                .padding(10.dp),
                backgroundColor = androidx.compose.material3.MaterialTheme.colorScheme.secondaryContainer,
                onClick = {
                    model.setEvent(HistoryContract.Event.refreshData)
                }) {
                Icon(
                    imageVector = Icons.Default.Refresh,
                    contentDescription = "refresh",
                    tint = androidx.compose.material3.MaterialTheme.colorScheme.onSecondaryContainer
                )
            }
        }
    }


    override fun onGobalEvent(event: GobalEvent, nav: Navigator) {
    }
}