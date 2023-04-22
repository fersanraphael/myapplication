package com.example.myapplication.data.source.network

import com.example.myapplication.data.model.network.TaskModel
import com.example.myapplication.domain.util.Result

/**
 * @author Raphael Fersan
 */
internal interface NetworkTaskDataSource {

    suspend fun getTaskFromNetwork(): Result<TaskModel>
}
