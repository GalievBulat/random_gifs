package com.kakadurf.randomgifs.presentation.randomgifslist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kakadurf.randomgifs.data.db.CachedGif
import com.kakadurf.randomgifs.data.db.DBCacheDao
import com.kakadurf.randomgifs.data.model.GiphyHttpData
import com.kakadurf.randomgifs.data.repository.GifRepository
import com.kakadurf.randomgifs.presentation.model.GifDto
import kotlinx.coroutines.flow.SharingStarted.Companion.WhileSubscribed
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import java.io.IOException

class GifListFragmentViewModel(
    private val repository: GifRepository,
    private val cache: DBCacheDao
) : ViewModel() {
    /*val randomGifsList =
        flow {
            emit(
                (1..10).asFlow().flatMapConcat {
                flowOf(repository.getRandomGif()).map{
                    with(it) {
                        GifDto(id, images.downsized.url, title)
                    }
                }
            }.toSet())
        }.stateIn(
            scope = viewModelScope,
            started = WhileSubscribed(5000),
            initialValue = null
        )*/

    suspend fun getRandomGifs(n: Int, onErrorAction: (IOException) -> Unit) =
        flow {
            try {
                val set = mutableSetOf<GiphyHttpData>()
                repeat(n) {
                    set.add(repository.getRandomGif())
                }
                emit(set as Set<GiphyHttpData>)
            } catch (e: IOException) {
                onErrorAction(e)
            }
        }.map { set ->
            set.map {
                with(it) {
                    GifDto(id, innerImages.gifImage.downloadUrl, title)
                }
            }
        }.stateIn(
            scope = viewModelScope,
            started = WhileSubscribed(5000),
            initialValue = null
        )

    suspend fun saveGif(gifDto: GifDto) =
        cache.saveToDB(
            CachedGif(
                repository.findGifMediaByUrl(gifDto.gifUrl).readBytes(),
                gifDto.gifUrl,
                gifDto.title,
                gifDto.id
            )
        )
}
