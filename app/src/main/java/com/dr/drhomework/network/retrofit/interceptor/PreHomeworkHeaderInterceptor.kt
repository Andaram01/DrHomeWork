package com.dr.drhomework.network.retrofit.interceptor

import okhttp3.Interceptor
import okhttp3.Response
import java.io.EOFException
import java.io.IOException

class PreHomeworkHeaderInterceptor : Interceptor {

    private val TAG = "TomasInterceptor"
    private val TOKEN_BEARER = "JWT  "

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val original = chain.request()

        original.newBuilder().apply {
            header("Authorization", "KakaoAK b3f97dd70180ac43065406a1ddaafad0")
            header("Authorization", "KakaoAK 613f61c19f330b6cc1d7fe46df56469e")
            method(original.method, original.body)
        }.let { builder ->
            return try {
                chain.proceed(builder.build())
            } catch (e: EOFException) {
                builder.removeHeader("X-Auth-Token")
                chain.proceed(builder.build())
            }

        }
    }
}
