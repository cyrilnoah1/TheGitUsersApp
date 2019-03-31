package com.rba.thegitusers.data.local.dataSources

import android.arch.lifecycle.LiveData
import android.arch.paging.LivePagedListBuilder
import android.arch.paging.PagedList
import com.rba.thegitusers.data.local.cache.CacheProvider
import com.rba.thegitusers.data.local.models.Contributor
import com.rba.thegitusers.data.local.models.Repository

class CacheDataSource : LocalGitDataSource {

    private val cache = CacheProvider.setup.githubDao()

    override fun saveRepositories(repositories: List<Repository>) {
        cache.saveRepos(repositories)
    }

    override fun getRepositoriesPaginated(searchKey: String): LiveData<PagedList<Repository>> {
        val factory = cache.getRepos(searchKey)
        val pageBuilder = LivePagedListBuilder<Int, Repository>(factory, PAGE_SIZE)
        return pageBuilder.build()
    }

    override fun saveContributors(contributors: List<Contributor>) {
        cache.saveContributors(contributors)
    }

    override fun getContributors(repoFullName: String): List<Contributor> {
        return cache.getContributors(repoFullName)
    }

    companion object {
        const val PAGE_SIZE: Int = 10
    }
}