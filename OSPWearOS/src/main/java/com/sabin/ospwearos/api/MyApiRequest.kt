package com.sabin.ospwearos.api

import org.json.JSONException
import org.json.JSONObject
import retrofit2.Response
import java.io.IOException

abstract class MyApiRequest {
    suspend fun <T : Any> apiRequest(call: suspend () -> Response<T>): T {
        val response = call.invoke()
        if(response.isSuccessful) {
            return response.body()!!
        }
        else {
            val error = response.errorBody()?.string()
            val message = StringBuilder()
            error?.let {
                try {
                    message.append(JSONObject(it).getString("success"))
                }
                catch (e: JSONException) {
                }
                message.append("\n")
            }
            message.append("Error code : ${response.code()}")
            throw IOException(message.toString())
        }
    }
}