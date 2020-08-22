package com.karschner.reppy

import android.util.Log
import com.github.kittinunf.fuel.httpGet
import com.github.kittinunf.result.Result
import org.json.JSONArray
import org.json.JSONObject

object GovDataAPI {
    val TAG = "GovDataAPI"
    private const val key = "c8RYQKYjg551TVXBqpXUcQI4M0LY0VA3YdaBwICn"
    private const val baseUrl = "https://api.propublica.org/congress/v1/members"

    fun getHouseRepresentative(state: String, district: String, onComplete: (JSONArray) -> Unit) {
        val httpAsync = "$baseUrl/house/$state/$district/current.json"
            .httpGet()
            .header(Pair("X-API-Key", key))
            .responseString { request, response, result ->
                if (result is Result.Success){
                    val data = JSONObject(result.get())
                    onComplete.invoke(data.getJSONArray("results"))
                } else if (result is Result.Failure){
                    Log.e(TAG, result.getException().toString())
                }
            }

        httpAsync.join()
    }

    fun getSenators(state: String, onComplete: (JSONArray) -> Unit){
        val httpAsync = "$baseUrl/senate/$state/current.json"
            .httpGet()
            .header(Pair("X-API-Key", key))
            .responseString { request, response, result ->
                if (result is Result.Success){
                    val data = JSONObject(result.get())
                    onComplete.invoke(data.getJSONArray("results"))
                } else if (result is Result.Failure) {
                    Log.e(TAG, result.getException().toString())
                }
            }

        httpAsync.join()
    }
}