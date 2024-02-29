package org.scode.mynote.ui.screen.detail

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.getScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.currentOrThrow
import daniel.avila.rnm.kmm.presentation.ui.common.state.ManagementResourceUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import org.company.app.utils.Log
import org.koin.compose.koinInject
import org.scode.mynote.base.mvi.BaseScreen
import org.scode.mynote.bean.NoteDataBean
import org.scode.mynote.repository.NoteRepository
import org.scode.mynote.ui.event.GobalEvent
import org.scode.mynote.ui.screen.history.HistoryContract
import org.scode.mynote.utils.TimeUtil.getNowTime
import org.scode.mynote.view.LoadingView

//详情界面
public data class DetailScreen(val id: Long = -1) : BaseScreen() {
    @Composable
    override fun CreateContent(nav: Navigator) {
        val model = getScreenModel<DetailModel>()
        val state by model.uiState.collectAsState()
        var _noteData = remember { MutableStateFlow(NoteDataBean()) }
        var noteDataState = _noteData.collectAsState()
        LaunchedEffect(Unit) {
            //刷新界面
            model.setEvent(DetailContract.Event.refreshData(id))
            model.effect.collectLatest {
                when (it) {
                    is DetailContract.Effect.back -> {
                        nav.pop()
                    }

                    is DetailContract.Effect.showToast -> {
                        sendGobalEvent(GobalEvent.ShowToast(it.data))
                    }
                }
            }
        }

        Scaffold(topBar = {
            TopAppBar(
                backgroundColor = androidx.compose.material3.MaterialTheme.colorScheme.primary,
                title = {},
                navigationIcon = {
                    IconButton(
                        onClick = {
                            nav.pop()
                        }) {
                        Icon(
                            tint = androidx.compose.material3.MaterialTheme.colorScheme.onPrimary,
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "back"
                        )
                    }
                }, actions = {
                    Button(modifier = Modifier
                        .size(width = 80.dp, height = 36.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor =
                            androidx.compose.material3.MaterialTheme.colorScheme.secondaryContainer
                        ),
                        onClick = {
                            _noteData.value = _noteData.value.copy(lastTime = getNowTime())
                            model.setEvent(DetailContract.Event.onSaveNote(_noteData.value))
                        }) {
                        Text(
                            "保存",
                            fontStyle = MaterialTheme.typography.h5.fontStyle,
                            color = androidx.compose.material3.MaterialTheme.colorScheme.onSecondaryContainer
                        )
                    }
                })
        }, floatingActionButton = {
            if (id != -1L) {
                FloatingActionButton(onClick = {
                    model.setEvent(DetailContract.Event.onDeleteNote(id))
                }, backgroundColor = androidx.compose.material3.MaterialTheme.colorScheme.secondaryContainer) {
                    Icon(
                        imageVector = Icons.Default.Delete, contentDescription = "delete",
                        tint = androidx.compose.material3.MaterialTheme.colorScheme.onSecondaryContainer
                    )
                }
            }
        }) {
            Box(
                modifier = Modifier
                    .background(Color.White)
                    .fillMaxSize(1f)
            ) {
                ManagementResourceUiState(
                    modifier = Modifier.fillMaxSize(1f),
                    resourceUiState = state.note,
                    needShowDialog = false,
                    loadingView = { LoadingView() },
                    successView = {
                        LaunchedEffect(Unit) {
                            _noteData.value = it
                        }
                        Column(
                            modifier = Modifier
                                .fillMaxSize(1f)
                                .background(androidx.compose.material3.MaterialTheme.colorScheme.background)
                        ) {
                            TextField(
                                label = { Text(text = "title") },
                                value = noteDataState.value.name,
                                singleLine = true,
                                shape = RoundedCornerShape(5.dp),
                                onValueChange = {
                                    _noteData.value = noteDataState
                                        .value.copy(name = it)
                                }, modifier = Modifier
                                    .padding(5.dp)
                                    .background(color = Color.White)
                                    .fillMaxHeight(0.12f)
                                    .fillMaxWidth(1f)
                            )
                            Text(
                                text = if (_noteData.value.lastTime.isEmpty()) {
                                    "Time:${getNowTime()}"
                                } else _noteData.value.lastTime,
                                modifier = Modifier.padding(start = 5.dp)
                            )
                            TextField(
                                label = { Text(text = "content") },
                                shape = RoundedCornerShape(5.dp),
                                value = noteDataState.value.noteData,
                                onValueChange = {
                                    _noteData.value = noteDataState.value.copy(noteData = it)
                                }, modifier = Modifier
                                    .padding(5.dp)
                                    .background(color = Color.White)
                                    .fillMaxHeight(0.7f)
                                    .fillMaxWidth(1f)
                            )
                        }
                    },
                    onTryAgain = {
                        model.setEvent(DetailContract.Event.refreshData(id))
                    },
                    onCheckAgain = {
                        model.setEvent(DetailContract.Event.refreshData(id))
                    }
                )
            }
        }
    }


    override fun onGobalEvent(event: GobalEvent, nav: Navigator) {
    }
}