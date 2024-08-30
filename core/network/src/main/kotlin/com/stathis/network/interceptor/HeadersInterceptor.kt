package com.stathis.network.interceptor

import okhttp3.Interceptor
import okhttp3.Response

class HeadersInterceptor(
    private val headers: Map<String, String>
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request().newBuilder()

        headers.forEach { (headerName, headerValue) ->
            request.addHeader(headerName, headerValue)
        }

        return chain.proceed(request.build())
    }
}