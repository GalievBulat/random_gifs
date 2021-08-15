package com.kakadurf.randomgifs.data.repository

import com.kakadurf.randomgifs.data.model.GiphyHttpData
import java.io.InputStream

interface GifRepository {
    suspend fun getRandomGif(): GiphyHttpData
    suspend fun findGifMediaByUrl(id: String): InputStream
}
