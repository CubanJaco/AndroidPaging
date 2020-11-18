package com.montbrungroup.androidpaguin.repository.safeapicall

import com.google.gson.Gson
import com.google.gson.stream.MalformedJsonException
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.io.IOException

open class SafeApiCall(private val dispatcher: CoroutineDispatcher = Dispatchers.IO) {

    @Suppress("UNCHECKED_CAST")
    suspend fun <T, E> safeApiCallWithRetry(
        errorClass: Class<E>,
        apiCall: suspend () -> T,
        refreshTokenCall: suspend () -> Any
    ): ResultWrapper<T, E> {

        val request = request(errorClass, apiCall)

        //error 401 ?
        val retry = if (request is ResultWrapper.GenericError && request.code == 401) {
            //obtener el apikey
            refreshTokenCall.invoke() as ResultWrapper<T, E>
        } else {
            //retornar la respuesta previa
            return request
        }

        //success el refresh token
        return if (retry is ResultWrapper.Success)
        //hacer el request de nuevo
            request(errorClass, apiCall)
        else
        //respuesta previa
            request

    }


    @Suppress("UNCHECKED_CAST")
    suspend fun <T, E> safeApiCall(
        errorClass: Class<E>,
        apiCall: suspend () -> T
    ): ResultWrapper<T, E> {
        return request(errorClass, apiCall)
    }

    @Suppress("UNCHECKED_CAST")
    private suspend fun <T, E> request(
        errorClass: Class<E>,
        apiCall: suspend () -> T
    ): ResultWrapper<T, E> {
        return withContext(dispatcher) {
            try {
                ResultWrapper.Success(apiCall.invoke()) as ResultWrapper<T, E>
            } catch (throwable: Throwable) {
                throwable.printStackTrace()
                when (throwable) {
                    is MalformedJsonException -> ResultWrapper.GenericError(
                        null,
                        null
                    ) as ResultWrapper<T, E>
                    is IOException -> ResultWrapper.NetworkError<E>() as ResultWrapper<T, E>
                    is HttpException -> {
                        val code = throwable.code()
                        val errorResponse = convertErrorBody(errorClass, throwable)
                        ResultWrapper.GenericError(code, errorResponse) as ResultWrapper<T, E>
                    }
                    else -> {
                        ResultWrapper.GenericError(null, null) as ResultWrapper<T, E>
                    }
                }
            }
        }
    }

    private fun <E> convertErrorBody(errorClass: Class<E>, throwable: HttpException): E? {
        return try {

            if (errorClass == String::class.java) {

                (throwable
                    .response()
                    ?.errorBody()
                    ?.string() ?: "") as E

            } else {

                throwable
                    .response()
                    ?.errorBody()
                    ?.string()
                    ?.let {
                        Gson().fromJson(it, errorClass)
                    }

            }
        } catch (exception: Exception) {
            null
        }
    }

}