package com.kdz.jarvis.repositories.extensions

import com.kdz.jarvis.network.result.NetworkResult
import com.kdz.jarvis.repositories.result.Resource

fun <T : Any, O : Any> NetworkResult<T>.mapToRepositoryResult(mapper: (T) -> O) = when(this) {
    is NetworkResult.Success -> Resource.Success(mapper(value))
    is NetworkResult.ApiError -> Resource.Error(error.status)
    is NetworkResult.NetworkError -> Resource.UnknownError(exception)
    is NetworkResult.UnknownError -> Resource.UnknownError(throwable)
}