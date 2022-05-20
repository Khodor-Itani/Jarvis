package com.kdz.jarvis.repositories.result

sealed class Resource<out T : Any> {
    data class Success<T : Any>(val value: T) : Resource<T>()
    object Loading : Resource<Nothing>()
    data class Error(val message: String) : Resource<Nothing>()
    data class UnknownError(val throwable: Throwable?) : Resource<Nothing>()
}