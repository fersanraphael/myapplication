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

internal interface GetTaskListFromLocalUseCase {

    operator fun invoke(): Flow<Result<List<TaskEntity>>>
}

internal class GetTaskListFromLocalUseCaseImpl constructor(
    private val coroutineDispatcher: CoroutineDispatcher = Dispatchers.IO,
    private val taskRepository: TaskRepository
) : GetTaskListFromLocalUseCase {

    override fun invoke(): Flow<Result<List<TaskEntity>>> {
        return flow {
            emit(taskRepository.getTaskListFromLocal())
        }.catch {
            emit(Result.Failure(it))
        }.flowOn(coroutineDispatcher)
    }
}
