package com.example.myapplication.data.source.network

import com.example.myapplication.data.model.network.TaskModel
import retrofit2.Response

/**
 * @author Raphael Fersan
 */
internal interface NetworkTaskDataSource {

    suspend fun getTaskFromNetwork(): Response<TaskModel>?
}
