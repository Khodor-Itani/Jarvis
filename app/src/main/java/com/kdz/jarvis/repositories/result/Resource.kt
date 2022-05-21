package com.kdz.jarvis.repositories.result

sealed class Resource<out T : Any> {

    val isSuccess: Boolean
        get() = this is Success<T>

    data class Success<T : Any>(val value: T) : Resource<T>()
    object Loading : Resource<Nothing>()
    data class Error(val message: String) : Resource<Nothing>()
    data class UnknownError(val throwable: Throwable?) : Resource<Nothing>()

    fun <O : Any> map(mapper: (T) -> O) : Resource<O> = when(this) {
        is Success -> Success(mapper(value))
        Loading -> Loading
        is Error -> this
        is UnknownError -> this
    }
}