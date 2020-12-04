package com.eton.kotlinretrofitpractice.repository

import com.eton.kotlinretrofitpractice.api.ApiManager
import com.eton.kotlinretrofitpractice.base.BaseRepository

class TestRepository : BaseRepository() {
    private val apiManager by lazy {
        ApiManager()
    }

    suspend fun hardUpdate() = safeApiCall {
        apiManager.api.getHardUpdate()
    }

}