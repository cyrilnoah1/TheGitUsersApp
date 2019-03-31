package com.rba.thegitusers.data.local.cache

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import com.rba.thegitusers.data.local.models.Contributor
import com.rba.thegitusers.data.local.models.Repository

/**
 * [RoomDatabase] to handle the C.R.U.D. operations through the respective Data Access Objects.
 */
@Database(entities = [Repository::class, Contributor::class], version = 1, exportSchema = false)
abstract class GithubDatabase : RoomDatabase() {

    abstract fun githubDao(): GitHubDao
}