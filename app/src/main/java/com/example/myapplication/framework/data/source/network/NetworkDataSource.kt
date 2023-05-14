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
internal class NetworkDataSource constructor(
    private val myApplicationRetrofit: MyApplicationRetrofit
) {

    private val networkService: NetworkService? by lazy {
        val retrofit: Retrofit? = myApplicationRetrofit.get()
        retrofit?.create(NetworkService::class.java)
    }

    internal suspend inline fun <reified R> get(
        url: String,
        headerMap: Map<String, String> = mapOf()
    ): Result<R> {
        return try {
            val response: Response<R> = networkService?.get(url, headerMap) ?: return Result.Failure()
            response.getResult()
        } catch (e: Exception) {
            Result.Failure(e)
        }
    }

    @Throws(Exception::class)
    private inline fun <reified R> Response<R>.getResult(): Result<R> {
        return if (isSuccessful) {
            val data: R = body() ?: return Result.Failure()
            Result.Success(data.asObject())
        } else {
            Result.Failure()
        }
    }

    @Throws(Exception::class)
    private inline fun <reified T> T.asObject(): T {
        val gson: Gson = GsonBuilder().create()
        val jsonElement: JsonElement = gson.toJsonTree(this)
        return gson.fromJson(jsonElement, T::class.java)
    }
}
