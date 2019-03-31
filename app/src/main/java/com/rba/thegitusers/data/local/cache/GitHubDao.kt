package com.rba.thegitusers.data.local.cache

import android.arch.paging.DataSource
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import com.rba.thegitusers.data.local.models.Contributor
import com.rba.thegitusers.data.local.models.Repository


/**
 * Data Access Object to perform C.R.U.D and retrieval operations on the local github
 * information data.
 */
@Dao
interface GitHubDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveRepo(repo: Repository)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveRepos(repos: List<Repository>)

    @Query("SELECT * FROM repository WHERE id = :id")
    fun getRepo(id: String): Repository

    @Query("SELECT * FROM repository WHERE name LIKE :searchKey")
    fun getRepos(searchKey: String): DataSource.Factory<Int, Repository>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveContributor(contributor: Contributor)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveContributors(contributors: List<Contributor>)

    @Query("SELECT * FROM contributor WHERE id = :id")
    fun getContributor(id: Int)

    @Query("SELECT * FROM contributor WHERE repo_full_name = :repoFullName")
    fun getContributors(repoFullName: String): List<Contributor>
}