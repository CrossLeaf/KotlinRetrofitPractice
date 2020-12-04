package com.eton.kotlinretrofitpractice.api

import com.eton.kotlinretrofitpractice.model.ErrorBean

sealed class ApiResult<out T : Any> {
    data class Success<out T : Any>(val data: T) : ApiResult<T>()
    data class Error(val httpErrorCode: Int?, val error: ErrorBean?) :
            ApiResult<Nothing>()

    object NetWorkError : ApiResult<Nothing>()
}
