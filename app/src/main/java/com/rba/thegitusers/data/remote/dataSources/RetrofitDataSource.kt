package com.rba.thegitusers.data.remote.dataSources

import android.util.Log
import com.rba.thegitusers.data.remote.models.Contributor
import com.rba.thegitusers.data.remote.models.Repository
import com.rba.thegitusers.data.remote.service.GithubService
import com.rba.thegitusers.data.remote.service.RepositoriesResponseBody
import com.rba.thegitusers.data.remote.service.ServiceProvider
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.functions.Consumer
import io.reactivex.schedulers.Schedulers

class RetrofitDataSource : RemoteGitDataSource {

    private val service = ServiceProvider.setup.create(GithubService::class.java)

    override fun getRepositories(searchKey: String, callback: RemoteGitDataSource.RepositoryResponse) {
        val disposable = CompositeDisposable()

        val handleSuccess = Consumer<RepositoriesResponseBody> {
            if (it?.repositories != null) {
                it.repositories.let { repos -> callback.onSuccess(repos) }
            } else {
                Log.e(TAG, RESPONSE_FAILURE_MESSAGE)
                callback.onFailure()
            }

            disposable.takeIf { disp -> !disp.isDisposed }?.dispose()
        }

        val handleError = Consumer<Throwable> {
            callback.onFailure()
            Log.e(TAG, it?.message)
            disposable.takeIf { disp -> !disp.isDisposed }?.dispose()
        }

        disposable.add(
            service.getRepositories(searchKey)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(handleSuccess, handleError)
        )
    }

    override fun getContributors(repoFullName: String, callback: RemoteGitDataSource.ContributorResponse) {
        val disposable = CompositeDisposable()

        val handleSuccess = Consumer<List<Contributor>> {
            if (it != null) {
                callback.onSuccess(it)
            } else {
                Log.e(TAG, RESPONSE_FAILURE_MESSAGE)
                callback.onFailure()
            }

            disposable.takeIf { disp -> !disp.isDisposed }?.dispose()
        }

        val handleError = Consumer<Throwable> {
            callback.onFailure()
            Log.e(TAG, it?.message)
            disposable.takeIf { disp -> !disp.isDisposed }?.dispose()
        }

        val splitted = repoFullName.split("/")
        disposable.add(
            service.getContributors(splitted[0], splitted[1])
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(handleSuccess, handleError)
        )
    }

    override fun getContributorRepos(loginName: String, callback: RemoteGitDataSource.RepositoryResponse) {
        val disposable = CompositeDisposable()

        val handleSuccess = Consumer<List<Repository>> {
            if (it != null) {
                callback.onSuccess(it)
            } else {
                Log.e(TAG, RESPONSE_FAILURE_MESSAGE)
                callback.onFailure()
            }

            disposable.takeIf { disp -> !disp.isDisposed }?.dispose()
        }

        val handleError = Consumer<Throwable> {
            callback.onFailure()
            Log.e(TAG, it?.message)
            disposable.takeIf { disp -> !disp.isDisposed }?.dispose()
        }

        disposable.add(
            service.getContributorRepos(loginName)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(handleSuccess, handleError)
        )
    }

    companion object {
        val TAG: String = RetrofitDataSource::class.java.simpleName
        const val RESPONSE_FAILURE_MESSAGE = "Failed to fetch the new data."
        const val EXCEPTION_FAILURE_MESSAGE = "An exception occurred while retrieving the response."
    }
}