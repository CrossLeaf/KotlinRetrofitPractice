package com.eton.kotlinretrofitpractice.model

/**
 * error from api response
 */
data class ErrorBean(
        val error_code: String,
        val message: String
)

data class HardUpdate  (
    val code: String,
    val `data`: Data,
    val msg: String,
    val status: String
)

data class Data(
    val android: List<Int>,
    val ios: List<Int>
)