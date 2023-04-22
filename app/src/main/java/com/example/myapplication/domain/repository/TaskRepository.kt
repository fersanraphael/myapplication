package com.example.myapplication.domain.repository

import com.example.myapplication.domain.entity.TaskEntity
import com.example.myapplication.domain.util.Result

/**
 * @author Raphael Fersan
 */
internal interface TaskRepository {

    suspend fun addTaskToLocal(taskEntity: TaskEntity): Result<TaskEntity>

    suspend fun getTaskFromNetwork(): Result<TaskEntity>

    suspend fun getTaskListFromLocal(): Result<List<TaskEntity>>
}
