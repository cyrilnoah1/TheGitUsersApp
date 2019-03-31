package com.rba.thegitusers.data.local.models

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity

@Entity(tableName = "contributor")
data class Contributor(
    @ColumnInfo(name = ID) val id: Int,
    @ColumnInfo(name = LOGIN) val loginName: String,
    @ColumnInfo(name = AVATAR_IMAGE) val profilePicUrl: String,
    @ColumnInfo(name = REPO_FULL_NAME) val repoFullName: String,
    @ColumnInfo(name = REPOS_URL) val reposLink: String
) {
    companion object {
        const val ID = "id"
        const val LOGIN = "login"
        const val AVATAR_IMAGE = "avatar_url"
        const val REPOS_URL = "repos_url"
        const val REPO_FULL_NAME = "repo_full_name"
    }
}