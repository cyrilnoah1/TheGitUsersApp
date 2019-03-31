package com.rba.thegitusers.data.local.models

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity(tableName = "repository")
data class Repository(
    @ColumnInfo(name = ID) @PrimaryKey val id: Int,
    @ColumnInfo(name = NAME) val name: String,
    @ColumnInfo(name = OWNER_IMAGE) val ownerImage: String,
    @ColumnInfo(name = DESCRIPTION) val description: String,
    @ColumnInfo(name = FULL_NAME) val fullName: String,
    @ColumnInfo(name = WATCHERS_COUNT) val watchersCount: Int,
    @ColumnInfo(name = URL) val url: String,
    @ColumnInfo(name = COMMITS_URL) val commitsUrl: String,
    @ColumnInfo(name = CONTRIBUTORS_URL) val contributorsUrl: String
) {

    companion object {
        const val ID = "id"
        const val NAME = "name"
        const val DESCRIPTION = "description"
        const val FULL_NAME = "full_name"
        const val WATCHERS_COUNT = "watchers_count"
        const val URL = "url"
        const val OWNER_IMAGE = "owner"
        const val COMMITS_URL = "commits_url"
        const val CONTRIBUTORS_URL = "contributors_url"
    }
}