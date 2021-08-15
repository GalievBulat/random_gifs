package com.kakadurf.randomgifs.data.http

import com.kakadurf.randomgifs.data.model.GiphyHttpResponse
import okhttp3.ResponseBody
import retrofit2.http.GET
import retrofit2.http.Url

interface GiphyService {
    @GET("/v1/gifs/random")
    suspend fun getRandomGif(): GiphyHttpResponse
    @GET
    suspend fun getMedia(@Url url: String?): ResponseBody
}
