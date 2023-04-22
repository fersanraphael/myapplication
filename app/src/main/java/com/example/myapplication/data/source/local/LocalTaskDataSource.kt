package com.example.myapplication.data.source.local

import com.example.myapplication.data.model.local.TaskDTO
import com.example.myapplication.domain.util.Result

/**
 * @author Raphael Fersan
 */
internal interface LocalTaskDataSource {

    suspend fun addTaskToLocal(taskDTO: TaskDTO): Result<TaskDTO>

    suspend fun getTaskListFromLocal(): Result<List<TaskDTO>>
}
