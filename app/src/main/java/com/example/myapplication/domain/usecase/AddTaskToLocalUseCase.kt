package com.example.myapplication.domain.usecase

import com.example.myapplication.domain.entity.TaskEntity
import com.example.myapplication.domain.repository.TaskRepository
import com.example.myapplication.domain.util.Result
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

/**
 * @author Raphael Fersan
 */

internal interface AddTaskToLocalUseCase {

    operator fun invoke(taskEntity: TaskEntity): Flow<Result<TaskEntity>>
}

internal class AddTaskToLocalUseCaseImpl constructor(
    private val coroutineDispatcher: CoroutineDispatcher = Dispatchers.IO,
    private val taskRepository: TaskRepository
) : AddTaskToLocalUseCase {

    override fun invoke(taskEntity: TaskEntity): Flow<Result<TaskEntity>> {
        return flow {
            emit(taskRepository.addTaskToLocal(taskEntity))
        }.catch {
            emit(Result.Failure(it))
        }.flowOn(coroutineDispatcher)
    }
}
