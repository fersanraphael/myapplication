package com.example.myapplication.data.repository

import com.example.myapplication.data.model.local.TaskDTO
import com.example.myapplication.data.model.local.toDTO
import com.example.myapplication.data.model.local.toEntity
import com.example.myapplication.data.model.network.TaskModel
import com.example.myapplication.data.model.network.toEntity
import com.example.myapplication.data.util.API_ENDPOINT_TASK
import com.example.myapplication.domain.entity.TaskEntity
import com.example.myapplication.domain.repository.TaskRepository
import com.example.myapplication.domain.util.Result
import com.example.myapplication.framework.data.source.local.GenericLocalDataSource
import com.example.myapplication.framework.data.source.network.GenericNetworkDataSource

/**
 * @author Raphael Fersan
 */
internal class TaskRepositoryImpl constructor(
    private val genericLocalDataSource: GenericLocalDataSource,
    private val genericNetworkDataSource: GenericNetworkDataSource
) : TaskRepository {

    override suspend fun addTaskToLocal(taskEntity: TaskEntity): Result<TaskEntity> {
        return try {
            when (val result: Result<TaskDTO> = genericLocalDataSource.add(taskEntity.toDTO())) {
                is Result.Failure -> Result.Failure(result.throwable)
                is Result.Success -> Result.Success(result.value.toEntity())
            }
        } catch (e: Exception) {
            Result.Failure(e)
        }
    }

    override suspend fun getTaskFromNetwork(): Result<TaskEntity> {
        return try {
            when (val result: Result<TaskModel> = genericNetworkDataSource.get(API_ENDPOINT_TASK)) {
                is Result.Failure -> Result.Failure(result.throwable)
                is Result.Success -> Result.Success(result.value.toEntity())
            }
        } catch (e: Exception) {
            Result.Failure(e)
        }
    }

    override suspend fun getTaskListFromLocal(): Result<List<TaskEntity>> {
        return try {
            when (val result: Result<List<TaskDTO>> = genericLocalDataSource.get()) {
                is Result.Failure -> Result.Failure(result.throwable)
                is Result.Success -> Result.Success(result.value.map(TaskDTO::toEntity))
            }
        } catch (e: Exception) {
            Result.Failure(e)
        }
    }
}
