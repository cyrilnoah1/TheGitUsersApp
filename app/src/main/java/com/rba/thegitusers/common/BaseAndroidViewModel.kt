package com.rba.thegitusers.common

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob

/**
 * Base ViewModel class for all the ViewModel classes that require [AndroidViewModel] for the
 * provision of the application context.
 *
 * This class also provides a [CoroutineScope] that dispatches on the [Dispatchers.Main] thread
 * and runs a [SupervisorJob] that gets cancelled when the [BaseAndroidViewModel] class gets cleared.
 */
abstract class BaseAndroidViewModel(application: Application) : AndroidViewModel(application) {

    /*
    SupervisorJob that handles each task as a separate child.
     */
    private val job = SupervisorJob()

    /**
     * [CoroutineScope] that dispatches the task on the [Dispatchers.Main] thread by default.
     */
    val uiScope = CoroutineScope(job + Dispatchers.Main)

    /**
     * [CoroutineScope] that dispatches the task on the [Dispatchers.IO] thread by default.
     */
    val ioScope = CoroutineScope(job + Dispatchers.IO)

    override fun onCleared() {
        super.onCleared()
        // Cancelling all the children of the SupervisorJob at once if the
        // ViewModel gets cleared.
        job.cancel()
    }
}