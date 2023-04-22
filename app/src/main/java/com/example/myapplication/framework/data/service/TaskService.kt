package com.example.myapplication.framework.data.service

import com.example.myapplication.data.model.network.TaskModel
import com.example.myapplication.data.util.API_ENDPOINT_TASK
import retrofit2.Response
import retrofit2.http.GET

/**
 * @author Raphael Fersan
 */
internal interface TaskService {

    @GET(API_ENDPOINT_TASK)
    suspend fun getTaskFromNetwork(): Response<TaskModel>
}
