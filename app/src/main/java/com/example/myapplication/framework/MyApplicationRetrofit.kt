package com.example.myapplication.framework

import com.example.myapplication.data.util.API_BASE_URL
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * @author Raphael Fersan
 */
internal class MyApplicationRetrofit constructor(
    private val okHttpClient: OkHttpClient
) {

    init {
        try {
            init()
        } catch (e: Exception) {
            print(e)
        }
    }

    private var retrofit: Retrofit? = null

    @Throws(
        IllegalStateException::class,
        NullPointerException::class
    )
    private fun init(): Retrofit? {
        retrofit = Retrofit.Builder()
            .baseUrl(API_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
        return retrofit
    }

    fun get(): Retrofit? {
        return retrofit ?: try {
            init()
        } catch (e: Exception) {
            null
        }
    }
}
