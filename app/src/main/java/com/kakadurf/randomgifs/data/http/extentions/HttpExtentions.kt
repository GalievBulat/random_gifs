package com.flatstack.qatesttask.data.guardiannews.extentions

import okhttp3.Interceptor
import okhttp3.Response

fun Interceptor.Chain.addQueriesToInterceptor(queryPair: Pair<String, String>): Response {

    val requestBuilder = request().url.newBuilder()
    requestBuilder.addQueryParameter(queryPair.first, queryPair.second)
    return requestBuilder.build().let { url ->
        this.proceed(request().newBuilder().url(url).build())
    }
}
