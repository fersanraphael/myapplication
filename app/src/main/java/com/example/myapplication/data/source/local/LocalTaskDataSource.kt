package com.example.myapplication.data.source.local

import com.example.myapplication.data.model.local.TaskDTO

/**
 * @author Raphael Fersan
 */
internal interface LocalTaskDataSource {

    suspend fun addTaskToLocal(taskDTO: TaskDTO): TaskDTO?

    suspend fun getTaskListFromLocal(): List<TaskDTO>?
}
