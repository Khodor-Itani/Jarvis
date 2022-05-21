package com.kdz.jarvis.di.modules

import com.google.gson.Gson
import com.kdz.jarvis.BuildConfig
import com.kdz.jarvis.network.cache.CacheHeaderInterceptor
import com.kdz.jarvis.network.result.NetworkResultAdapterFactory
import com.kdz.jarvis.network.services.MarvelService
import com.kdz.jarvis.network.signing.MarvelApiKeyGenerator
import com.kdz.jarvis.network.signing.MarvelApiKeyInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Named
import javax.inject.Singleton
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.CallAdapter
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
@InstallIn(SingletonComponent::class)
object RetrofitModule {

    @Provides
    @Singleton
    fun provideMarvelService(retrofit: Retrofit) = retrofit.create(MarvelService::class.java)

    @Provides
    fun provideRetrofit(
        converterFactory: Converter.Factory,
        callAdapterFactory: CallAdapter.Factory,
        okHttpClient: OkHttpClient
    ) = Retrofit.Builder()
        .baseUrl(MarvelService.BASE_URL)
        .addCallAdapterFactory(callAdapterFactory)
        .addConverterFactory(converterFactory)
        .client(okHttpClient)
        .build()

    @Provides
    fun provideNetworkResultCallAdapterFactor(): CallAdapter.Factory = NetworkResultAdapterFactory()

    @Provides
    fun provideGsonConverterFactory(gson: Gson): Converter.Factory =
        GsonConverterFactory.create(gson)

    @Provides
    fun provideGson() = Gson()

    @Provides
    fun provideOkHttpClient(
        @Named("ApiKey")
        apiKeyInterceptor: Interceptor,
        @Named("Cache")
        cacheHeaderInterceptor: Interceptor,
        @Named("Logging")
        loggingInterceptor: Interceptor?
    ) = OkHttpClient.Builder()
        .addInterceptor(apiKeyInterceptor)
        .addInterceptor(cacheHeaderInterceptor)
        .apply {
            loggingInterceptor?.let(::addInterceptor)
        }
        .build()

    @Provides
    @Named("Logging")
    fun provideLoggingInterceptor(): Interceptor? = if(BuildConfig.DEBUG) {
        HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
    } else {
        null
    }

    @Provides
    @Named("Cache")
    fun provideCacheHeaderInterceptor() : Interceptor = CacheHeaderInterceptor()

    @Provides
    fun provideMarvelApiProvider() = MarvelApiKeyGenerator(
        BuildConfig.MARVEL_API_PUBLIC_KEY,
        BuildConfig.MARVEL_API_PRIVATE_KEY
    )

    @Provides
    @Named("ApiKey")
    fun provideApiKeyInterceptor(apiKeyProvider: MarvelApiKeyGenerator): Interceptor =
        MarvelApiKeyInterceptor(apiKeyProvider)
}