package com.example.myapplication.data.source.network

import com.example.myapplication.domain.util.Result

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
