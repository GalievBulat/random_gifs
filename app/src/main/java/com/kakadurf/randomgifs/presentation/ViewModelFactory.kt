package com.kakadurf.randomgifs.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.kakadurf.randomgifs.data.db.DBCacheDao
import com.kakadurf.randomgifs.data.repository.GifRepository
import com.kakadurf.randomgifs.presentation.randomgifslist.GifListFragmentViewModel
import com.kakadurf.randomgifs.presentation.savedgifslist.SavedGifsListFragmentViewModel
import java.lang.IllegalArgumentException

class ViewModelFactory(
    private val repository: GifRepository,
    private val cache: DBCacheDao
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T =
        when (modelClass) {
            GifListFragmentViewModel::class.java -> GifListFragmentViewModel(repository, cache)
            SavedGifsListFragmentViewModel::class.java -> SavedGifsListFragmentViewModel(cache)
            else -> throw IllegalArgumentException()
        } as T
}
