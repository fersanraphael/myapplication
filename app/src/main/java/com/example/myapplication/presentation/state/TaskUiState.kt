package com.example.myapplication.presentation.state

import com.example.myapplication.domain.entity.TaskEntity

/**
 * @author Raphael Fersan
 */

/*
 * LocalTaskUiState related.
 */

internal sealed class LocalTaskUiState : TaskUiState() {

    internal data class AddTaskSuccess constructor(
        val taskEntity: TaskEntity
    ) : LocalTaskUiState()

    internal data class GetTaskListSuccess constructor(
        val taskEntityList: List<TaskEntity>
    ) : LocalTaskUiState()
}

/*
 * NetworkTaskUiState related.
 */

internal sealed class NetworkTaskUiState : TaskUiState() {

    internal data class GetTaskSuccess constructor(
        val taskEntity: TaskEntity
    ) : NetworkTaskUiState()
}

/*
 * TaskUiState related.
 */

internal sealed class TaskUiState {

    internal data class Failure constructor(
        val throwable: Throwable? = null
    ) : TaskUiState()

    internal object Loading : TaskUiState()
}
