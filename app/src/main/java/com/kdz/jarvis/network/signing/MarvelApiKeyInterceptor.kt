package com.kdz.jarvis.network.signing

import android.os.SystemClock
import com.kdz.jarvis.BuildConfig
import javax.inject.Inject
import okhttp3.Interceptor
import okhttp3.Response

class MarvelApiKeyInterceptor
@Inject constructor(
    val apiKeyGenerator: MarvelApiKeyGenerator
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request()
        val timeStamp = SystemClock.elapsedRealtime().toString()

        val url = request.url
            .newBuilder()
            .addQueryParameter(TIMESTAMP_KEY, timeStamp)
            .addQueryParameter(API_KEY_KEY, BuildConfig.MARVEL_API_PUBLIC_KEY)
            .addQueryParameter(HASH_KEY, apiKeyGenerator.generate(timeStamp))
            .build()

        request = request.newBuilder().url(url).build()

        return chain.proceed(request)
    }

    companion object {
        private const val TIMESTAMP_KEY = "ts"
        private const val API_KEY_KEY = "apikey"
        private const val HASH_KEY = "hash"
    }
}