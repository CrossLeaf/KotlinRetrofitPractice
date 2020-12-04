package com.eton.kotlinretrofitpractice.base

import com.eton.kotlinretrofitpractice.api.ApiResult
import com.eton.kotlinretrofitpractice.model.ErrorBean
import com.google.gson.Gson
import retrofit2.HttpException
import retrofit2.Response
import kotlin.math.log

open class BaseRepository {
    suspend fun <T : Any> safeApiCall(
            apiCall: suspend () -> Response<out T>
    ): ApiResult<T> {
        return safeApiResult(apiCall)
    }

    private suspend fun <T : Any> safeApiResult(
            apiCall: suspend () -> Response<out T>
    ): ApiResult<T> {
        return try {
            val response: Response<out T> = apiCall.invoke()
            if (response.isSuccessful) {
                println("api type success")
                // call api success
                ApiResult.Success(data = response.body()!!)
            } else {
                println("api type error")
                val code: Int = response.code()
                val errorBean: ErrorBean? = convertErrorBody(response)
                errorBean?.error_code?.let {
                    when {
                        // error code = xxx, do something
                        it.contentEquals("xxx") -> {
                            println("api error")
                        }
                    }
                }
                ApiResult.Error(code, errorBean)
            }
        } catch (throwable: Throwable) {
            println("throwable message = ${throwable.message}")
            ApiResult.Error(null, null)
        }
    }

    private fun convertErrorBody(errorData: Any): ErrorBean? {
        return when (errorData) {
            is Response<*> -> {
                try {
                    errorData.errorBody()?.string()?.let {
                        Gson().fromJson(it, ErrorBean::class.java)
                    }
                } catch (exception: Exception) {
                    null
                }
            }
            is HttpException -> {
                try {
                    errorData.response()?.errorBody()?.string()?.let {
                        Gson().fromJson(it, ErrorBean::class.java)
                    }
                } catch (exception: Exception) {
                    null
                }
            }
            else -> {
                null
            }
        }
    }
}
