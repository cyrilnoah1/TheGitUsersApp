package com.rba.thegitusers.features.repository.repositories

import android.app.Application
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.rba.thegitusers.R
import com.rba.thegitusers.common.BaseAndroidViewModel
import com.rba.thegitusers.common.EMPTY_STRING
import com.rba.thegitusers.data.repos.GithubRepo
import kotlinx.coroutines.launch

class RepositoriesViewModel(private val app: Application) : BaseAndroidViewModel(app) {

    private val githubRepo = GithubRepo()
    val obsError = MutableLiveData<String>()
    val obsIsDataLoading = MutableLiveData<Boolean>()

    fun fetchRepositories(searchKey: String) {
        obsIsDataLoading.postValue(true)
        uiScope.launch {
            if (!githubRepo.fetchRepositories(searchKey)) {
                obsError.postValue(app.resources.getString(R.string.repository_retrieval_failure_msg))
                obsIsDataLoading.postValue(false)
            } else {
                obsError.postValue(EMPTY_STRING)
                obsIsDataLoading.postValue(false)
            }
        }
    }

    fun getRepositoriesPaged(searchKey: String) = githubRepo.getRepositoriesPaged(searchKey)

    class Factory(private val application: Application) : ViewModelProvider.AndroidViewModelFactory(application) {

        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return RepositoriesViewModel(application) as T
        }
    }
}