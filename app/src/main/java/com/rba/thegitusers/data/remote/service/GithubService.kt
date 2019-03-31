package com.rba.thegitusers.data.remote.service

import com.google.gson.annotations.SerializedName
import com.rba.thegitusers.data.QUERY_PARAMETER_KEY
import com.rba.thegitusers.data.remote.models.Contributor
import com.rba.thegitusers.data.remote.models.Repository
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * Remote service to fetch for the repositories list.
 */
interface GithubService {

    @GET("search/repositories")
    fun getRepositories(@Query(QUERY_PARAMETER_KEY) q: String): Observable<RepositoriesResponseBody>

    @GET("repos/{path1}/{path2}/contributors")
    fun getContributors(
        @Path("path1") contributorsLink: String,
        @Path("path2") contributorsLink2: String
    ): Observable<List<Contributor>>

    @GET("users/{loginName}/repos")
    fun getContributorRepos(@Path("loginName") loginName: String): Observable<List<Repository>>
}


data class RepositoriesResponseBody(
    @SerializedName(TOTAL_COUNT) val totalCount: Int,
    @SerializedName(INCOMPLETE_RESULTS) val incompleteResults: Boolean,
    @SerializedName(ITEMS) val repositories: List<Repository>
) {

    companion object {
        const val TOTAL_COUNT = "total_count"
        const val INCOMPLETE_RESULTS = "incomplete_results"
        const val ITEMS = "items"
    }
}