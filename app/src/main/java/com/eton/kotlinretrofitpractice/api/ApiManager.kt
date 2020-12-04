package com.eton.kotlinretrofitpractice.api

import com.eton.kotlinretrofitpractice.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.util.concurrent.TimeUnit


class ApiManager {

    object ApiConfig {
        const val TIME_OUT_CONNECT = 30
        const val TIME_OUT_READ = 30
        const val TIME_OUT_WRITE = 30

        var baseUrl =
                if (BuildConfig.DEBUG)
                    "https://d1e00325-f7c5-4ee8-819e-f7e1624cb720.mock.pstmn.io/"
                else
                    "release-api"
        var baseWsUrl = if (BuildConfig.DEBUG) "" else ""
    }

    val api: ApiService = provideRetrofit().create(ApiService::class.java)

    private fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .client(provideOkHttpClient())
                .baseUrl(ApiConfig.baseUrl)
                .build()
    }

    private fun provideOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
                .addNetworkInterceptor(provideLoggingInterceptor())
                .addInterceptor { chain ->
                    val original = chain.request()

                    // Request customization: add request headers
                    val requestBuilder = original.newBuilder()
                            .header("mo-platform", "android")
                            .header("x-app-ver", BuildConfig.VERSION_NAME)

                    val request = requestBuilder.build()
                    chain.proceed(request)
                }
                .connectTimeout(ApiConfig.TIME_OUT_CONNECT.toLong(), TimeUnit.SECONDS)
                .readTimeout(ApiConfig.TIME_OUT_READ.toLong(), TimeUnit.SECONDS)
                .writeTimeout(ApiConfig.TIME_OUT_WRITE.toLong(), TimeUnit.SECONDS)
                .build()
    }

    private fun provideLoggingInterceptor(): HttpLoggingInterceptor {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        return interceptor
    }
}