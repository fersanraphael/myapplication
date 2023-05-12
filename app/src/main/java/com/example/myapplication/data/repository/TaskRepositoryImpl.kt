package com.example.myapplication.data.repository

import com.example.myapplication.data.model.local.TaskDTO
import com.example.myapplication.data.model.local.toDTO
import com.example.myapplication.data.model.local.toEntity
import com.example.myapplication.data.model.network.TaskModel
import com.example.myapplication.data.model.network.toEntity
import com.example.myapplication.data.source.local.LocalTaskDataSource
import com.example.myapplication.data.util.API_ENDPOINT_TASK
import com.example.myapplication.domain.entity.TaskEntity
import com.example.myapplication.domain.repository.TaskRepository
import com.example.myapplication.domain.util.Result
import com.example.myapplication.framework.data.source.network.NetworkDataSource

/**
 * @author Raphael Fersan
 */
internal class TaskRepositoryImpl constructor(
    private val localTaskDataSource: LocalTaskDataSource,
    private val networkDataSource: NetworkDataSource
) : TaskRepository {

    override suspend fun addTaskToLocal(taskEntity: TaskEntity): Result<TaskEntity> {
        return try {
            when (val result: Result<TaskDTO> = localTaskDataSource.addTaskToLocal(taskEntity.toDTO())) {
                is Result.Failure -> Result.Failure(result.throwable)
                is Result.Success -> Result.Success(result.value.toEntity())
            }
        } catch (e: Exception) {
            Result.Failure(e)
        }
    }

    override suspend fun getTaskFromNetwork(): Result<TaskEntity> {
        return try {
            when (val result: Result<TaskModel> = networkDataSource.get(API_ENDPOINT_TASK, TaskModel::class.java)) {
                is Result.Failure -> Result.Failure(result.throwable)
                is Result.Success -> Result.Success(result.value.toEntity())
            }
        } catch (e: Exception) {
            Result.Failure(e)
        }
    }

    override suspend fun getTaskListFromLocal(): Result<List<TaskEntity>> {
        return try {
            when (val result: Result<List<TaskDTO>> = localTaskDataSource.getTaskListFromLocal()) {
                is Result.Failure -> Result.Failure(result.throwable)
                is Result.Success -> Result.Success(result.value.map(TaskDTO::toEntity))
            }
        } catch (e: Exception) {
            Result.Failure(e)
        }
    }
}
