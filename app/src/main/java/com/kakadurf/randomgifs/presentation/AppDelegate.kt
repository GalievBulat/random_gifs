package com.kakadurf.randomgifs.presentation

import android.app.Application
import com.kakadurf.randomgifs.data.db.CacheDb
import com.kakadurf.randomgifs.data.http.GiphyDataService
import com.kakadurf.randomgifs.data.repository.GifRepositoryImpl
import okhttp3.logging.HttpLoggingInterceptor

class AppDelegate : Application() {
    companion object {
        lateinit var viewModelFactory: ViewModelFactory
    }
    override fun onCreate() {
        super.onCreate()
        val dataService = GiphyDataService("9FzKPVQY8YNTTk700n7BPjkbalicEylS")
        val db = CacheDb.create(this)
        val httpService = dataService.run {
            getService(
                getBaseRetrofit(
                    getClient(
                        listOf(
                            getApiKeyInterceptor(),
                            HttpLoggingInterceptor().apply {
                                setLevel(HttpLoggingInterceptor.Level.BASIC)
                            }
                        )
                    )
                )
            )
        }
        viewModelFactory = ViewModelFactory(GifRepositoryImpl(httpService), db.getCacheDao())
    }
}
