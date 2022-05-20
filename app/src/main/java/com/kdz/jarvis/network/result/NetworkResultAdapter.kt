package com.kdz.jarvis.network.result

import com.kdz.jarvis.network.models.Error
import java.lang.reflect.Type
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.CallAdapter
import retrofit2.Converter

class NetworkResultAdapter<S : Any>(
    private val successType: Type,
    private val errorBodyConverter: Converter<ResponseBody, Error>
) : CallAdapter<S, Call<NetworkResult<S>>> {

    override fun responseType(): Type = successType

    override fun adapt(call: Call<S>): Call<NetworkResult<S>> {
        return NetworkResultCall(call, errorBodyConverter)
    }
}