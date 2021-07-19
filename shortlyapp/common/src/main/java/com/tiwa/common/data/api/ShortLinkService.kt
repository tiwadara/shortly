package com.tiwa.common.data.api

import com.tiwa.common.data.model.ApiResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ShortLinkService {

    @GET("shorten")
    suspend fun getShortLinks(@Query("url") url: String): ApiResponse

}