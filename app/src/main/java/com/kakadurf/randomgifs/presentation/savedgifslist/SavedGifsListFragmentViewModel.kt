package com.kakadurf.randomgifs.presentation.savedgifslist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kakadurf.randomgifs.data.db.DBCacheDao
import com.kakadurf.randomgifs.presentation.model.GifDto
import kotlinx.coroutines.flow.SharingStarted.Companion.WhileSubscribed
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

class SavedGifsListFragmentViewModel(private val cache: DBCacheDao) : ViewModel() {
    val savedGifsList: StateFlow<List<GifDto>?> =
        cache.pullAllFromDB().map { list ->
            list.map {
                with(it) {
                    GifDto(id, title, url, gifData)
                }
            }
        }
            .stateIn(
                scope = viewModelScope,
                started = WhileSubscribed(5000),
                initialValue = null
            )
    suspend fun deleteFromSaved(id: String) =
        cache.deleteFromDb(cache.pullFromDB(id))
}
