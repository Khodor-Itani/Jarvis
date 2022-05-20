package com.kdz.jarvis.network.result

import com.kdz.jarvis.network.models.Error
import java.io.IOException

sealed class NetworkResult<out T : Any> {
    data class Success<T : Any>(val body: T) : NetworkResult<T>()
    data class ApiError(val error: Error, val code: Int) : NetworkResult<Nothing>()
    data class NetworkError(val error: IOException) : NetworkResult<Nothing>()
    data class UnknownError(val error: Throwable?) : NetworkResult<Nothing>()
}