package com.example.myapplication.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.domain.entity.TaskEntity
import com.example.myapplication.domain.usecase.AddTaskToLocalUseCase
import com.example.myapplication.domain.usecase.GetTaskFromNetworkUseCase
import com.example.myapplication.domain.usecase.GetTaskListFromLocalUseCase
import com.example.myapplication.domain.util.Result
import com.example.myapplication.framework.MyApplicationRealm
import com.example.myapplication.presentation.state.LocalTaskUiState
import com.example.myapplication.presentation.state.NetworkTaskUiState
import com.example.myapplication.presentation.state.TaskUiState
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.plus

/**
 * @author Raphael Fersan
 */
internal class MainViewModel constructor(
    private val addTaskToLocalUseCase: AddTaskToLocalUseCase,
    private val getTaskFromNetworkUseCase: GetTaskFromNetworkUseCase,
    private val getTaskListFromLocalUseCase: GetTaskListFromLocalUseCase,
    private val myApplicationRealm: MyApplicationRealm
) : ViewModel() {

    init {
        registerExceptionHandler()
    }

    private val _taskUiStateFlow = MutableStateFlow<TaskUiState?>(null)

    val taskUiStateFlow: StateFlow<TaskUiState?> = _taskUiStateFlow.stateIn(
        scope = viewModelScope,
        started = SharingStarted.Eagerly,
        initialValue = TaskUiState.Loading
    )

    fun addTaskToLocal(taskEntity: TaskEntity) {
        addTaskToLocalUseCase(taskEntity).onEach {
            _taskUiStateFlow.value = when (it) {
                is Result.Success -> LocalTaskUiState.AddTaskSuccess(it.value)
                is Result.Failure -> TaskUiState.Failure(it.throwable)
            }
        }.onStart {
            _taskUiStateFlow.value = TaskUiState.Loading
        }.launchIn(viewModelScope)
    }

    fun getTaskFromNetwork() {
        getTaskFromNetworkUseCase().onEach {
            _taskUiStateFlow.value = when (it) {
                is Result.Success -> NetworkTaskUiState.GetTaskSuccess(it.value)
                is Result.Failure -> TaskUiState.Failure(it.throwable)
            }
        }.onStart {
            _taskUiStateFlow.value = TaskUiState.Loading
        }.launchIn(viewModelScope)
    }

    fun getTaskListFromLocal() {
        getTaskListFromLocalUseCase().onEach {
            _taskUiStateFlow.value = when (it) {
                is Result.Success -> LocalTaskUiState.GetTaskListSuccess(it.value)
                is Result.Failure -> TaskUiState.Failure(it.throwable)
            }
        }.onStart {
            _taskUiStateFlow.value = TaskUiState.Loading
        }.launchIn(viewModelScope)
    }

    private fun registerExceptionHandler() {
        val coroutineExceptionHandler = CoroutineExceptionHandler { _, throwable ->
            _taskUiStateFlow.value = TaskUiState.Failure(throwable)
        }
        viewModelScope.plus(coroutineExceptionHandler)
    }

    override fun onCleared() {
        super.onCleared()

        myApplicationRealm.close()
    }
}
