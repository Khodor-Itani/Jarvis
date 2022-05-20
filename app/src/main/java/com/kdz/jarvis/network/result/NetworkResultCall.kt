package com.kdz.jarvis.network.result

import com.kdz.jarvis.network.models.Error
import java.io.IOException
import okhttp3.Request
import okhttp3.ResponseBody
import okio.Timeout
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Converter
import retrofit2.Response

internal class NetworkResultCall<S : Any>(
    private val delegate: Call<S>,
    private val errorConverter: Converter<ResponseBody, Error>
) : Call<NetworkResult<S>> {

    override fun enqueue(callback: Callback<NetworkResult<S>>) {
        return delegate.enqueue(object : Callback<S> {
            override fun onResponse(call: Call<S>, response: Response<S>) {
                val body = response.body()
                val code = response.code()
                val error = response.errorBody()

                if (response.isSuccessful) {
                    if (body != null) {
                        callback.onResponse(
                            this@NetworkResultCall,
                            Response.success(NetworkResult.Success(body))
                        )
                    } else {
                        // Response is successful but the body is null
                        callback.onResponse(
                            this@NetworkResultCall,
                            Response.success(NetworkResult.UnknownError(null))
                        )
                    }
                } else {
                    val errorBody = when {
                        error == null -> null
                        error.contentLength() == 0L -> null
                        else -> try {
                            errorConverter.convert(error)
                        } catch (ex: Exception) {
                            null
                        }
                    }
                    if (errorBody != null) {
                        callback.onResponse(
                            this@NetworkResultCall,
                            Response.success(NetworkResult.ApiError(errorBody, code))
                        )
                    } else {
                        callback.onResponse(
                            this@NetworkResultCall,
                            Response.success(NetworkResult.UnknownError(null))
                        )
                    }
                }
            }

            override fun onFailure(call: Call<S>, throwable: Throwable) {
                val NetworkResult = when (throwable) {
                    is IOException -> NetworkResult.NetworkError(throwable)
                    else -> NetworkResult.UnknownError(throwable)
                }
                callback.onResponse(this@NetworkResultCall, Response.success(NetworkResult))
            }
        })
    }

    override fun isExecuted() = delegate.isExecuted

    override fun clone() = NetworkResultCall(delegate.clone(), errorConverter)

    override fun isCanceled() = delegate.isCanceled

    override fun cancel() = delegate.cancel()

    override fun execute(): Response<NetworkResult<S>> {
        throw UnsupportedOperationException("NetworkResultCall doesn't support execute")
    }

    override fun request(): Request = delegate.request()

    override fun timeout(): Timeout = delegate.timeout()
}