package com.rba.thegitusers.data.remote.models

import com.google.gson.annotations.SerializedName
import com.rba.thegitusers.data.local.models.Contributor

data class Repository(
    @SerializedName(ID) val id: Int?,
    @SerializedName(NAME) val name: String?,
    @SerializedName(OWNER) val owner: Contributor?,
    @SerializedName(DESCRIPTION) val description: String?,
    @SerializedName(FULL_NAME) val fullName: String?,
    @SerializedName(WATCHERS_COUNT) val watchersCount: Int?,
    @SerializedName(URL) val url: String?,
    @SerializedName(COMMITS_URL) val commitsUrl: String?,
    @SerializedName(CONTRIBUTORS_URL) val contributorsUrl: String?
) {

    companion object {
        const val ID = "id"
        const val NAME = "name"
        const val DESCRIPTION = "description"
        const val FULL_NAME = "full_name"
        const val WATCHERS_COUNT = "watchers_count"
        const val URL = "url"
        const val OWNER = "owner"
        const val COMMITS_URL = "commits_url"
        const val CONTRIBUTORS_URL = "contributors_url"
    }
}