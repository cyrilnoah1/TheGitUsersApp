package com.rba.thegitusers.data.remote.dataSources

import com.rba.thegitusers.data.remote.models.Contributor
import com.rba.thegitusers.data.remote.models.Repository

/**
 * Remote DataSource interface to perform API calls for
 * fetching Github data.
 */
interface RemoteGitDataSource {

    /**
     * Fetches remote [Repository] details.
     */
    fun getRepositories(searchKey: String, callback: RepositoryResponse) {
        throw IMPLEMENTATION_EXCP
    }

    /**
     * Fetches remote [Contributor] details.
     */
    fun getContributors(repoFullName: String, callback: ContributorResponse) {
        throw IMPLEMENTATION_EXCP
    }

    /**
     * Fetches remote [Contributor]s [Repository] list details.
     */
    fun getContributorRepos(loginName: String, callback: RepositoryResponse) {
        throw IMPLEMENTATION_EXCP
    }

    /**
     * Callbacks for remote [Repository] list response.
     */
    interface RepositoryResponse {
        fun onSuccess(repos: List<Repository>)
        fun onFailure()
    }

    /**
     * Callbacks for remote [Contributor] list response.
     */
    interface ContributorResponse {
        fun onSuccess(contributors: List<Contributor>)
        fun onFailure()
    }

    companion object {

        // Runtime Exception to warn that the developer has not
        // implemented the logic for the interface member.
        val IMPLEMENTATION_EXCP = RuntimeException("Not implemented in child class.")
    }
}