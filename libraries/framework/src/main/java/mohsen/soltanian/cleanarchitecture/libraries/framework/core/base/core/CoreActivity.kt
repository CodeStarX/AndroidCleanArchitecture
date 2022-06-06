package mohsen.soltanian.cleanarchitecture.libraries.framework.core.base.core

import android.content.IntentFilter
import android.net.ConnectivityManager
import android.os.Bundle
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import mohsen.soltanian.cleanarchitecture.libraries.framework.R
import mohsen.soltanian.cleanarchitecture.libraries.framework.core.base.annotation.*
import mohsen.soltanian.cleanarchitecture.libraries.framework.extensions.exitToast
import mohsen.soltanian.cleanarchitecture.libraries.framework.receivers.ConnectionCheck
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import mohsen.soltanian.cleanarchitecture.libraries.framework.extensions.handleBackPress
import timber.log.Timber

abstract class CoreActivity : AppCompatActivity(), ConnectionCheck.ConnectivityReceiverListener {


    private lateinit var connectionCheck: ConnectionCheck

    lateinit var exitToast: Toast
    private var backPressedOnce = false

    private var layout: Layout? = null
    private var handleBackPress: HandleBackPress? = null
    private var layoutResId = -1
    private var lockBackPress: LockBackPress? = null
    private var handleDoubleBackPress: HandleDoubleBackPress? = null
    private var checkNetworkConnection: CheckNetworkConnection? = null


    abstract fun onViewReady(bundle: Bundle?)

    open fun onViewListener() {}

    open fun observeState() {}

    open fun observeUi() {}

    open fun performDataBinding(layoutID: Int) {}

    open fun networkAvailable() {}

    open fun networkNotAvailable() {}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initAttributes()
        setContentView(layoutResId)
        performDataBinding(layoutID = layoutResId)
        observeUi()
        observeState()
        onViewReady(savedInstanceState)
        onViewListener()

    }

    private fun initAttributes() {
        layout = javaClass.getAnnotation(Layout::class.java)
        handleBackPress = javaClass.getAnnotation(HandleBackPress::class.java)
        lockBackPress = javaClass.getAnnotation(LockBackPress::class.java)
        handleDoubleBackPress = javaClass.getAnnotation(HandleDoubleBackPress::class.java)
        checkNetworkConnection = javaClass.getAnnotation(CheckNetworkConnection::class.java)

        if (layout == null) {
            throw Exception("Activity layout is Null")
        } else {
            if (layout!!.value == -1) {
                throw Exception("Activity layout value is -1")
            } else {
                layoutResId = layout!!.value
            }
        }

        if (handleDoubleBackPress?.value == true) {
            handleBackPress {
                if (backPressedOnce) {
                    finish()
                }
                backPressedOnce = true
                exitToast = exitToast(getString(R.string.app_exit_label))
                exitToast.apply { show() }
                lifecycleScope.launch {
                    delay(2000)
                    backPressedOnce = false
                }
            }
        }
        if (handleBackPress?.value == true) {
            handleBackPress{
                onBackPressedCallBack()
            }
        }
        if (lockBackPress?.value == true) {
           handleBackPress {
               Timber.tag("BackPress").i(": disable back press button")
           }
        }

        if (checkNetworkConnection?.value == true) {
            registerNetworkReceiver()
        }
    }

    override fun onResume() {
        super.onResume()
        window.clearFlags(WindowManager.LayoutParams.FLAG_SECURE)
    }

    override fun onStop() {
        super.onStop()
        if (checkNetworkConnection?.value == true) unregisterNetworkReceiver()

    }

    override fun onPause() {
        super.onPause()
        window.setFlags(
            WindowManager.LayoutParams.FLAG_SECURE,
            WindowManager.LayoutParams.FLAG_SECURE
        )
    }

    override fun onDestroy() {
        super.onDestroy()
        if (::exitToast.isInitialized) {
            exitToast.apply { cancel() }
        }
    }

    /**
     * Override onBackPressedCallBack in Activity
     */
    protected open fun onBackPressedCallBack() {}


    private fun registerNetworkReceiver() {
        connectionCheck = ConnectionCheck()
        connectionCheck.setConnectivityReceiverListener(this)
        registerReceiver(
            connectionCheck,
            IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION)
        )
    }

    private fun unregisterNetworkReceiver() {
        try {
            unregisterReceiver(connectionCheck)
        } catch (e: Throwable) {
            e.printStackTrace()
        }
    }

    override fun onNetworkConnectionChanged(isConnected: Boolean) {
        if (isConnected) {
            networkAvailable()
        } else {
            networkNotAvailable()
        }
    }
}
