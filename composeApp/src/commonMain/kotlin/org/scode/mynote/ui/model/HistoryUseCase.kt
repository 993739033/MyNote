package org.scode.mynote.ui.model

import daniel.avila.rnm.kmm.domain.interactors.type.BaseUseCase
import daniel.avila.rnm.kmm.domain.interactors.type.BaseUseCaseFlow
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import org.company.app.utils.Log
import org.scode.mynote.bean.NoteDataBean
import org.scode.mynote.repository.NoteRepository

//历史数据
class HistoryUseCase(
    private val repository: NoteRepository,
    val dispatcher: CoroutineDispatcher
) {
    init {
        Log.e("HistoryUseCase init")
    }

    //获取历史数据
    suspend fun getHistoryData() =
        object : BaseUseCaseFlow<Unit, List<NoteDataBean>>(dispatcher) {
            override suspend fun build(param: Unit): Flow<List<NoteDataBean>> {
                delay(2000)
                return repository.getNoteList()
            }
        }


    //获取单个详情
    suspend fun getHistoryDetailData(id: Long) =
        object : BaseUseCase<Long, NoteDataBean>(dispatcher) {
            override suspend fun block(param: Long): NoteDataBean {
                return repository.getNote(param)
            }
        }(id)

    //更新数据
    suspend fun updateHistoryData(noteDataBean: NoteDataBean) =
        object : BaseUseCase<NoteDataBean, Unit>(dispatcher) {
            override suspend fun block(param: NoteDataBean) {
                repository.updateNote(param)
            }
        }(noteDataBean)


    //插入数据
    suspend fun createHistoryData(noteDataBean: NoteDataBean) =
        object : BaseUseCase<NoteDataBean, Unit>(dispatcher) {
            override suspend fun block(param: NoteDataBean) {
                repository.createNote(param)
            }
        }(noteDataBean)



    //获取单个详情
    suspend fun deleteDetailData(id: Long) = object : BaseUseCase<Long, Unit>(dispatcher) {
        override suspend fun block(param: Long) {
            repository.removeNote(param)
        }
    }(id)

}