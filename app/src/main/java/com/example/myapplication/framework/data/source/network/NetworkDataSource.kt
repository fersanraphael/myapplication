package com.example.myapplication.framework.data.source.network

import com.example.myapplication.domain.util.Result
import com.example.myapplication.framework.MyApplicationRetrofit
import com.example.myapplication.framework.data.service.NetworkService
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.JsonElement
import retrofit2.Response
import retrofit2.Retrofit

/**
 * @author Raphael Fersan
 */

internal interface NetworkDataSource {

    suspend fun <R> get(
        url: String,
        responseType: Class<R>,
        headerMap: Map<String, String> = mapOf()
    ): Result<R>
}

internal class NetworkDataSourceImpl constructor(
    private val myApplicationRetrofit: MyApplicationRetrofit
) : NetworkDataSource {

    private val networkService: NetworkService? by lazy {
        val retrofit: Retrofit? = myApplicationRetrofit.get()
        retrofit?.create(NetworkService::class.java)
    }

    override suspend fun <R> get(
        url: String,
        responseType: Class<R>,
        headerMap: Map<String, String>
    ): Result<R> {
        return try {
            val response: Response<R> = networkService?.get(url, headerMap) ?: return Result.Failure()
            response.getResultOf(responseType)
        } catch (e: Exception) {
            Result.Failure(e)
        }
    }

    @Throws(Exception::class)
    private fun <R> Response<R>.getResultOf(classOf: Class<R>): Result<R> {
        return if (isSuccessful) {
            val data: R = body() ?: return Result.Failure()
            Result.Success(data.asObject(classOf))
        } else {
            Result.Failure()
        }
    }

    @Throws(Exception::class)
    private fun <T> T.asObject(classOf: Class<T>): T {
        val gson: Gson = GsonBuilder().create()
        val jsonElement: JsonElement = gson.toJsonTree(this)
        return gson.fromJson(jsonElement, classOf)
    }
}
