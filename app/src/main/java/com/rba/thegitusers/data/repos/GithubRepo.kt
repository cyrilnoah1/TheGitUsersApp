package com.rba.thegitusers.data.repos

import android.util.Log
import com.rba.thegitusers.common.EMPTY_STRING
import com.rba.thegitusers.data.local.dataSources.CacheDataSource
import com.rba.thegitusers.data.remote.dataSources.RemoteGitDataSource
import com.rba.thegitusers.data.remote.dataSources.RetrofitDataSource
import com.rba.thegitusers.data.remote.models.Contributor
import com.rba.thegitusers.data.remote.models.Repository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine
import com.rba.thegitusers.data.local.models.Repository as LocalRepository
import com.rba.thegitusers.data.local.models.Contributor as LocalContributor

class GithubRepo {

    private val cache = CacheDataSource()
    private val remote = RetrofitDataSource()

    private val job = SupervisorJob()
    private val ioScope = CoroutineScope(Dispatchers.IO)

    /**
     * Function to fetch and store the repositories in the cache.
     */
    suspend fun fetchRepositories(searchKey: String): Boolean = suspendCoroutine {

        remote.getRepositories(searchKey, object : RemoteGitDataSource.RepositoryResponse {

            override fun onSuccess(repos: List<Repository>) {
                // Converting to remote model to local model and saving
                // it in the cache.
                ioScope.launch {
                    cache.saveRepositories(repos.map { repo ->
                        LocalRepository(
                            id = repo.id,
                            name = repo.name ?: EMPTY_STRING,
                            ownerImage = repo.owner?.profilePicUrl ?: EMPTY_STRING,
                            ownerId = repo.owner?.id ?: -1,
                            description = repo.description ?: EMPTY_STRING,
                            fullName = repo.fullName ?: EMPTY_STRING,
                            watchersCount = repo.watchersCount ?: 0,
                            htmlUrl = repo.htmlUrl ?: EMPTY_STRING,
                            hasDownloads = repo.hasDownloads ?: false,
                            hasProjects = repo.hasProjects ?: false,
                            hasIssues = repo.hasIssues ?: false,
                            hasWiki = repo.hasWiki ?: false,
                            commitsUrl = repo.commitsUrl ?: EMPTY_STRING,
                            contributorsUrl = repo.contributorsUrl ?: EMPTY_STRING
                        )
                    })
                }

                it.resume(true)
            }

            override fun onFailure() {
                Log.e(TAG, "Failed to fetch for the data.")
                it.resume(false)
            }
        })
    }

    /**
     * Function to fetch and store the repositories in the cache.
     */
    suspend fun fetchRepositoriesByContributor(loginName: String): Boolean = suspendCoroutine {

        remote.getContributorRepos(loginName, object : RemoteGitDataSource.RepositoryResponse {

            override fun onSuccess(repos: List<Repository>) {
                // Converting to remote model to local model and saving
                // it in the cache.
                ioScope.launch {
                    cache.saveRepositories(repos.map { repo ->
                        LocalRepository(
                            id = repo.id,
                            name = repo.name ?: EMPTY_STRING,
                            ownerImage = repo.owner?.profilePicUrl ?: EMPTY_STRING,
                            ownerId = repo.owner?.id ?: -1,
                            description = repo.description ?: EMPTY_STRING,
                            fullName = repo.fullName ?: EMPTY_STRING,
                            watchersCount = repo.watchersCount ?: 0,
                            htmlUrl = repo.htmlUrl ?: EMPTY_STRING,
                            hasDownloads = repo.hasDownloads ?: false,
                            hasProjects = repo.hasProjects ?: false,
                            hasIssues = repo.hasIssues ?: false,
                            hasWiki = repo.hasWiki ?: false,
                            commitsUrl = repo.commitsUrl ?: EMPTY_STRING,
                            contributorsUrl = repo.contributorsUrl ?: EMPTY_STRING
                        )
                    })
                }

                it.resume(true)
            }

            override fun onFailure() {
                Log.e(TAG, "Failed to fetch for the data.")
                it.resume(false)
            }
        })
    }

    /**
     * Function to listen to the live changes in the locally cached repositories
     * and retrieve the data.
     */
    fun getRepositoriesPaged(searchKey: String) = cache.getRepositoriesPaginated(searchKey)

    /**
     * Function to retrieve required [LocalRepository] based on the [id] from the cache.
     */
    fun getRepository(id: Int) = cache.getRepository(id)

    fun getReposByContriutor(contribId: Int) = cache.getRepositoriesByOwnerId(contribId)

    /**
     * Function to fetch for the required contributors for a repository.
     */
    suspend fun fetchContributors(repoFullName: String): Boolean = suspendCoroutine {

        remote.getContributors(repoFullName, object : RemoteGitDataSource.ContributorResponse {

            override fun onSuccess(contributors: List<Contributor>) {
                // Converting to remote model to local model and saving
                // it in the cache.
                ioScope.launch {
                    cache.saveContributors(contributors.map { cont ->
                        LocalContributor(
                            id = cont.id,
                            loginName = cont.loginName ?: EMPTY_STRING,
                            profilePicUrl = cont.profilePicUrl ?: EMPTY_STRING,
                            repoFullName = repoFullName,
                            reposLink = cont.reposLink ?: EMPTY_STRING
                        )
                    })
                }

                it.resume(true)
            }

            override fun onFailure() {
                Log.e(TAG, "Failed to fetch for the data.")
                it.resume(false)
            }
        })
    }

    /**
     * Function to retrive [LocalContributor]s from the cache.
     */
    fun getContributors(repoFullName: String) = cache.getContributors(repoFullName)


    /**
     * Function to retrieve required [LocalContributor] based on the [id] from the cache.
     */
    fun getContributor(id: Int) = cache.getContributor(id)

    companion object {
        val TAG: String = GithubRepo::class.java.simpleName
    }
}