package com.example.myapplication.data.repository

import com.example.myapplication.data.model.local.TaskDTO
import com.example.myapplication.data.model.local.toDTO
import com.example.myapplication.data.model.local.toEntity
import com.example.myapplication.data.model.network.TaskModel
import com.example.myapplication.data.model.network.toEntity
import com.example.myapplication.data.source.local.LocalTaskDataSource
import com.example.myapplication.data.source.network.NetworkTaskDataSource
import com.example.myapplication.domain.entity.TaskEntity
import com.example.myapplication.domain.repository.TaskRepository
import com.example.myapplication.domain.util.Result
import retrofit2.Response

/**
 * @author Raphael Fersan
 */
internal class TaskRepositoryImpl constructor(
    private val localTaskDataSource: LocalTaskDataSource,
    private val networkTaskDataSource: NetworkTaskDataSource
) : TaskRepository {

    override suspend fun addTaskToLocal(taskEntity: TaskEntity): Result<TaskEntity> {
        return try {
            val result: TaskDTO? = localTaskDataSource.addTaskToLocal(taskEntity.toDTO())
            if (result != null) {
                Result.Success(result.toEntity())
            } else {
                Result.Failure()
            }
        } catch (e: Exception) {
            Result.Failure(e)
        }
    }

    override suspend fun getTaskFromNetwork(): Result<TaskEntity> {
        return try {
            val response: Response<TaskModel> = networkTaskDataSource.getTaskFromNetwork()
            if (response.isSuccessful) {
                val result: TaskModel = response.body() ?: return Result.Failure()
                Result.Success(result.toEntity())
            } else {
                Result.Failure()
            }
        } catch (e: Exception) {
            Result.Failure(e)
        }
    }

    override suspend fun getTaskListFromLocal(): Result<List<TaskEntity>> {
        return try {
            val result: List<TaskDTO>? = localTaskDataSource.getTaskListFromLocal()
            if (result != null) {
                Result.Success(result.map(TaskDTO::toEntity))
            } else {
                Result.Failure()
            }
        } catch (e: Exception) {
            Result.Failure(e)
        }
    }
}
