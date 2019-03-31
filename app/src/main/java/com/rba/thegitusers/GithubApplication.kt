package com.rba.thegitusers

import android.app.Application
import android.content.Context

/**
 * [Application] class that helps in providing the application context
 * at the desired locations.
 */
class GithubApplication : Application(){

    override fun onCreate() {
        super.onCreate()
        appContext = applicationContext
    }

    companion object {
        private var appContext: Context? = null

        /**
         * Function to provide the application context.
         *
         * This function should never return [null] as the [appContext] is set right when the
         * application is opened. Hence, a [RuntimeException] is explicitly thrown to notify that
         * there has been a terrible failure.
         */
        fun getAppContext(): Context = appContext ?: throw RuntimeException("Could not provide application context.")
    }
}