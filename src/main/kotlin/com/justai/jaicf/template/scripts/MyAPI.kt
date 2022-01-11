package com.justai.jaicf.template.scripts

import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response

/*
fun postRequest(url: String, body: String): String {
    val client = OkHttpClient()
    val request = Request.Builder()
        .url(url)
        .get()
        .build()
    val response =  client.newCall(request).execute()
    return if (response.code == 200) {
        response.body!!.string()
    } else {
        println(response.code)
        "400"
    }
}*/

fun getRequest(url: String): String {
    val client = OkHttpClient()
    val request = Request.Builder()
        .url(url)
        .get()
        .build()
    val response =  client.newCall(request).execute()
    return if (response.code == 200) {
        response.body!!.string()
    } else {
        println(response.code)
        "400"
    }
}
