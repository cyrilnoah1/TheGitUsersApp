package com.rba.thegitusers.common

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.view.ViewGroup
import com.rba.thegitusers.R

/**
 * Base Activity class that helps in managing the indentical operations required by multiple
 * Activities in the Application.
 */
abstract class BaseActivity : AppCompatActivity() {

    // Broadcast Receiver to check for the network connectivity.
    private val networkState = NetworkStateReceiver()
    private val networkFilter = IntentFilter("android.net.conn.CONNECTIVITY_CHANGE")

    // Boolean to inform whether the internet connectivity is available at the moment.
    private var isNetworkAvailable: Boolean = true

    /**
     * [Snackbar] to display "No network connection" message based on the current state
     * of the network connectivity.
     */
    val networkSnack by lazy {
        getRootView()?.snack(getString(R.string.no_internt_msg), Snackbar.LENGTH_LONG)
    }

    /**
     * Function (Factory Method) to retrieve the root view of the child Activity.
     *
     * The root view is used to display the [Snackbar].
     */
    abstract fun getRootView(): ViewGroup?

    /**
     * Function to notify the current state of the network connectivity.
     */
    abstract fun getNetworkAvailability(isConnected: Boolean)

    override fun onResume() {
        super.onResume()
        registerReceiver(networkState, networkFilter)
    }

    override fun onPause() {
        super.onPause()
        unregisterReceiver(networkState)
    }

    /**
     * Function to return the current state of the network connectivity.
     */
    fun getNetworkState() = isNetworkAvailable

    /**
     * [BroadcastReceiver] class to listen to the network state.
     */
    inner class NetworkStateReceiver : BroadcastReceiver() {

        override fun onReceive(context: Context?, intent: Intent?) {
            context?.run {
                val cm = this.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
                val activeNetwork: NetworkInfo? = cm.activeNetworkInfo
                val isConnected: Boolean = activeNetwork?.isConnected == true

                getNetworkAvailability(isConnected)
                isNetworkAvailable = isConnected

                if (!isConnected) {
                    networkSnack?.show()
                } else {
                    networkSnack?.dismiss()
                }
            }
        }
    }

    companion object {
        val TAG: String = BaseActivity::class.java.simpleName
    }
}