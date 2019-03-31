package com.rba.thegitusers.data.local.dataSources

import android.arch.lifecycle.LiveData
import android.arch.paging.PagedList
import com.rba.thegitusers.data.local.models.Contributor
import com.rba.thegitusers.data.local.models.Repository

/**
 * Interface to fetch the required Github details from the
 * implemented DataSource.
 */
interface LocalGitDataSource {

    fun saveRepository(repository: Repository) {
        throw IMPLEMENTATION_EXCP
    }

    fun saveRepositories(repositories: List<Repository>) {
        throw IMPLEMENTATION_EXCP
    }

    fun getRepositories(searchKey: String): List<Repository> {
        throw IMPLEMENTATION_EXCP
    }

    fun getRepositoriesPaginated(searchKey: String): LiveData<PagedList<Repository>> {
        throw IMPLEMENTATION_EXCP
    }

    fun getRepository(id: Int): Repository {
        throw IMPLEMENTATION_EXCP
    }


    fun saveContributor(contributor: Contributor) {
        throw IMPLEMENTATION_EXCP
    }

    fun saveContributors(contributors: List<Contributor>) {
        throw IMPLEMENTATION_EXCP
    }

    fun getContributors(repoFullName: String): List<Contributor> {
        throw IMPLEMENTATION_EXCP
    }

    fun getContributor(id: Int): Contributor {
        throw IMPLEMENTATION_EXCP
    }

    fun getContributorRepositories(contributorName: String): List<Repository> {
        throw IMPLEMENTATION_EXCP
    }

    companion object {

        // Runtime Exception to warn that the developer has not
        // implemented the logic for the interface member.
        val IMPLEMENTATION_EXCP = RuntimeException("Not implemented in child class.")
    }
}