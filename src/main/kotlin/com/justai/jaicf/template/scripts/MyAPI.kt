package com.justai.jaicf.template.scripts

import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody

fun postRequest(url: String, body: RequestBody): String? {
    val client = OkHttpClient()
    val request = Request.Builder()
        .url(url)
        .post(body)
        .build()
    val response =  client.newCall(request).execute()
    return response.body()?.string()
}

fun getRequest(url: String): String? {
    val client = OkHttpClient()
    val request = Request.Builder()
        .url(url)
        .build()
    val response =  client.newCall(request).execute()
    return response.body()?.string()
//    return if (response.body()?.string() is String) {
//        response.body()?.string()!!
//    } else {
//        println(response.code())
//        "400"
//    }
}
