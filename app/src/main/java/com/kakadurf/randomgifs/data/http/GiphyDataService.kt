package com.kakadurf.randomgifs.data.http

import com.flatstack.qatesttask.data.guardiannews.extentions.addQueriesToInterceptor
import com.kakadurf.randomgifs.BuildConfig
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

const val GIPHY_TIMEOUT = 30L

class GiphyDataService(private val api_key: String) {

    fun getApiKeyInterceptor() = Interceptor {
        it.addQueriesToInterceptor(
            "api_key" to api_key
        )
    }
    fun getClient(
        interceptors: Collection<Interceptor>
    ): OkHttpClient = OkHttpClient.Builder()
        .connectTimeout(GIPHY_TIMEOUT, TimeUnit.SECONDS).apply {
            for (interceptor in interceptors) {
                addInterceptor(interceptor)
            }
        }
        .build()
    fun getBaseRetrofit(client: OkHttpClient): Retrofit = Retrofit.Builder()
        .client(client)
        .baseUrl(BuildConfig.GIPHY_API_BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    fun getService(retrofit: Retrofit): GiphyService =
        retrofit.create(
            GiphyService::class.java
        )
}
