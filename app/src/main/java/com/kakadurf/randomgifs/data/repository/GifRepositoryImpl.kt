package com.kakadurf.randomgifs.data.repository

import com.kakadurf.randomgifs.data.http.GiphyService
import com.kakadurf.randomgifs.data.model.GiphyHttpData

class GifRepositoryImpl(private val service: GiphyService) : GifRepository {
    override suspend fun getRandomGif(): GiphyHttpData =
        service.getRandomGif().data

    override suspend fun findGifMediaByUrl(url: String) =
        service.getMedia(url).byteStream()
}
