package com.jizhe7550.myapplication.api

import com.jizhe7550.myapplication.api.bean.ApiResponse
import java.io.IOException
import com.jizhe7550.myapplication.api.core.ApiResult
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.coroutineScope

open class BaseRepository {

    suspend fun <T : Any> apiCall(call: suspend () -> ApiResponse<T>): ApiResponse<T> {
        return call.invoke()
    }

    suspend fun <T : Any> safeApiCall(call: suspend () -> ApiResult<T>, errorMessage: String): ApiResult<T> {
        return try {
            call()
        } catch (e: Exception) {
            // An exception was thrown when calling the API so we're converting this to an IOException
            ApiResult.Error(IOException(errorMessage, e))
        }
    }

//    suspend fun <T : Any> executeResponse(response: ApiResponse<T>, successBlock: (suspend CoroutineScope.() -> Unit)? = null,
//                                          errorBlock: (suspend CoroutineScope.() -> Unit)? = null): ApiResult<T> {
//        return coroutineScope {
//            if (response.errorCode == -1) {
//                errorBlock?.let { it() }
//                ApiResult.Error(IOException(response.errorMsg))
//            } else {
//                successBlock?.let { it() }
//                ApiResult.Success(response.data)
//            }
//        }
//    }

    suspend fun <T : Any> executeResponse(response: T, successBlock: (suspend CoroutineScope.() -> Unit)? = null,
                                          errorBlock: (suspend CoroutineScope.() -> Unit)? = null): ApiResult<T> {
        return coroutineScope {
            response?.let {
                ApiResult.Success(response)
            }
        }
    }

}