package com.rba.thegitusers.data.local.cache

import android.arch.persistence.room.Room
import com.rba.thegitusers.GithubApplication

/**
 * Singleton to instantiate and provide the local cache handling object.
 */
object CacheProvider {

    private const val NEWS_DATABASE_NAME = "news"

    /**
     * Lazily provides the [GithubDatabase] object that helps in maintaining the local cache.
     */
    val setup: GithubDatabase by lazy {

        Room.databaseBuilder(
            GithubApplication.getAppContext(),
            GithubDatabase::class.java,
            NEWS_DATABASE_NAME
        ).build()
    }
}