package com.example.myapplication.framework.data.source.network

import com.example.myapplication.data.model.network.TaskModel
import com.example.myapplication.data.source.network.NetworkTaskDataSource
import com.example.myapplication.domain.util.Result
import com.example.myapplication.framework.MyApplicationRetrofit
import com.example.myapplication.framework.data.service.TaskService
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

    override suspend fun getTaskFromNetwork(): Result<TaskModel> {
        return try {
            val response: Response<TaskModel> = taskService?.getTaskFromNetwork() ?: return Result.Failure()
            if (response.isSuccessful) {
                Result.Success(response.body() as TaskModel)
            } else {
                Result.Failure()
            }
        } catch (e: Exception) {
            Result.Failure(e)
        }
    }
}
