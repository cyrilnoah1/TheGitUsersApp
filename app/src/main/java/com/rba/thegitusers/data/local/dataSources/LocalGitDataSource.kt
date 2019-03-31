package com.rba.thegitusers.data.local.dataSources

import com.rba.thegitusers.data.local.models.Contributor
import com.rba.thegitusers.data.local.models.Repository

/**
 * Interface to fetch the required Github details from the
 * implemented DataSource.
 */
interface LocalGitDataSource {

    fun getRepositories(searchKey: String): List<Repository> {
        throw IMPLEMENTATION_EXCP
    }

    fun getRepository(id: Int): Repository {
        throw IMPLEMENTATION_EXCP
    }

    fun getContributors(): List<Contributor> {
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