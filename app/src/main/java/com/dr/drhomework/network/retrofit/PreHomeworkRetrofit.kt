package com.dr.drhomework.network.retrofit

import com.dr.drhomework.BuildConfig
import com.dr.drhomework.network.retrofit.interceptor.PreHomeworkHeaderInterceptor
import com.dr.drhomework.network.retrofit.interceptor.PreHomeworkLogInterceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

open class PreHomeworkRetrofit {
    companion object {
        private const val TIMEOUT_READ: Long = 15L
        private const val TIMEOUT_CONNECT: Long = 15L
        private const val TIMEOUT_WRITE: Long = 15L
    }


    protected fun <S> getRequestRetrofit(service: Class<S>): S {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(getOkHttpClient())
            .baseUrl(BuildConfig.BASE_URL)
            .build()
            .create(service)
    }


    private fun getOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .readTimeout(TIMEOUT_READ, TimeUnit.SECONDS)
            .connectTimeout(TIMEOUT_CONNECT, TimeUnit.SECONDS)
            .writeTimeout(TIMEOUT_WRITE, TimeUnit.SECONDS)
            .also { clientBuilder ->
                clientBuilder.run {
                    interceptors().clear()
                    addInterceptor(PreHomeworkHeaderInterceptor())
                    addInterceptor(PreHomeworkLogInterceptor())
                }
            }
            .build()
    }
}