package com.example.myapplication.framework.data.service

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.HeaderMap
import retrofit2.http.Url

/**
 * @author Raphael Fersan
 */
internal interface NetworkService {

    @GET
    suspend fun <A> get(
        @Url url: String,
        @HeaderMap headerMap: Map<String, String>
    ): Response<A>
}
