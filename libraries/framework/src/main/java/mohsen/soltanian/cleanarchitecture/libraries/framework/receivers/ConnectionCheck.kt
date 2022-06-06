package mohsen.soltanian.cleanarchitecture.libraries.framework.receivers

import android.annotation.SuppressLint
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import mohsen.soltanian.cleanarchitecture.libraries.framework.extensions.isInternetAvailable

class ConnectionCheck : BroadcastReceiver() {

    private var listener: ConnectivityReceiverListener? = null

    @SuppressLint("UnsafeProtectedBroadcastReceiver")
    override fun onReceive(context: Context, intent: Intent) {
        if (listener != null) listener!!.onNetworkConnectionChanged(context.isInternetAvailable())
    }

    fun setConnectivityReceiverListener(listener: ConnectivityReceiverListener?) {
        this.listener = listener
    }

    interface ConnectivityReceiverListener {
        fun onNetworkConnectionChanged(isConnected: Boolean)
    }
}