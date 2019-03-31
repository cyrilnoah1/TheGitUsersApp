package com.rba.thegitusers.features.contributor.contributor

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

class ContributorViewModel(private val app: Application) : BaseAndroidViewModel(app) {

    val obsError = MutableLiveData<String>()
    val obsIsDataLoading = MutableLiveData<Boolean>()
    val obsContributor = MutableLiveData<Contributor>()
    val obsRepos = MutableLiveData<List<Repository>>()

    private val githubRepo = GithubRepo()

    fun fetchUserRepos(loginName: String, contribId: Int) {
        obsIsDataLoading.postValue(true)
        ioScope.launch {
            if (githubRepo.fetchRepositoriesByContributor(loginName)) {
                obsRepos.postValue(githubRepo.getReposByContriutor(contribId))
                obsIsDataLoading.postValue(false)
                obsError.postValue(EMPTY_STRING)
            } else {
                obsError.postValue(app.resources.getString(R.string.contributors_retrieval_failure_msg))
                obsIsDataLoading.postValue(false)
            }
        }
    }

    fun getContributor(id: Int) {
        ioScope.launch {
            obsContributor.postValue(githubRepo.getContributor(id))
        }
    }

    class Factory(private val application: Application) : ViewModelProvider.AndroidViewModelFactory(application) {

        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return ContributorViewModel(application) as T
        }
    }
}