package com.rba.thegitusers.features.repository.repository

import android.app.Application
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.rba.thegitusers.R
import com.rba.thegitusers.common.BaseAndroidViewModel
import com.rba.thegitusers.common.EMPTY_STRING
import com.rba.thegitusers.data.local.models.Contributor
import com.rba.thegitusers.data.local.models.Repository
import com.rba.thegitusers.data.repos.GithubRepo
import kotlinx.coroutines.launch

class RepositoryViewModel(private val app: Application) : BaseAndroidViewModel(app) {

    val obsError = MutableLiveData<String>()
    val obsIsDataLoading = MutableLiveData<Boolean>()
    val obsRepo = MutableLiveData<Repository>()
    val obsContributors = MutableLiveData<List<Contributor>>()

    private val githubRepo = GithubRepo()

    fun getRepoDetails(id: Int) {
        obsIsDataLoading.postValue(true)
        ioScope.launch {
            obsRepo.postValue(githubRepo.getRepository(id))
            obsIsDataLoading.postValue(false)
        }
    }

    fun fetchContributors(repoFullName: String) {
        obsIsDataLoading.postValue(true)
        ioScope.launch {
            if (githubRepo.fetchContributors(repoFullName)) {
                obsContributors.postValue(githubRepo.getContributors(repoFullName))
                obsIsDataLoading.postValue(false)
                obsError.postValue(EMPTY_STRING)
            } else {
                obsError.postValue(app.resources.getString(R.string.contributors_retrieval_failure_msg))
                obsIsDataLoading.postValue(false)
            }
        }
    }

    class Factory(private val application: Application) : ViewModelProvider.AndroidViewModelFactory(application) {

        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return RepositoryViewModel(application) as T
        }
    }
}