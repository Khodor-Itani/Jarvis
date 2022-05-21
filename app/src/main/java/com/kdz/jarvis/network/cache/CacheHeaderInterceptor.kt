package com.kdz.jarvis.network.cache

import okhttp3.Interceptor
import okhttp3.Response

private const val CACHE_MAX_AGE_SECONDS = 60

class CacheHeaderInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        return chain.proceed(chain.request()).newBuilder()
            .header("Cache-Control", "public, max-age=$CACHE_MAX_AGE_SECONDS")
            .removeHeader("Pragma")
            .build()
    }
}