package com.eton.kotlinretrofitpractice.api

import com.eton.kotlinretrofitpractice.model.HardUpdate
import retrofit2.Response
import retrofit2.http.GET

interface ApiService {

    @GET("version")
    suspend fun getHardUpdate(): Response<HardUpdate>
}