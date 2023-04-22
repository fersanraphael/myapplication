package com.example.myapplication.framework.data.source.network

import com.example.myapplication.data.model.network.TaskModel
import com.example.myapplication.data.service.TaskService
import com.example.myapplication.data.source.network.NetworkTaskDataSource
import com.example.myapplication.framework.MyApplicationRetrofit
import retrofit2.Response
import retrofit2.Retrofit

/**
 * @author Raphael Fersan
 */
internal class NetworkTaskDataSourceImpl constructor(
    myApplicationRetrofit: MyApplicationRetrofit
) : NetworkTaskDataSource {

    private val taskService: TaskService? by lazy {
        val retrofit: Retrofit? = myApplicationRetrofit.get()
        retrofit?.create(TaskService::class.java)
    }

    override suspend fun getTaskFromNetwork(): Response<TaskModel>? {
        return taskService?.getTaskFromNetwork()
    }
}
