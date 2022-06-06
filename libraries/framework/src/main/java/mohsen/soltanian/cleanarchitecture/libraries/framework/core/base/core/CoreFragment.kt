package mohsen.soltanian.cleanarchitecture.libraries.framework.core.base.core

import android.content.Context
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import mohsen.soltanian.cleanarchitecture.libraries.framework.R
import mohsen.soltanian.cleanarchitecture.libraries.framework.core.base.annotation.*
import mohsen.soltanian.cleanarchitecture.libraries.framework.extensions.exitToast
import mohsen.soltanian.cleanarchitecture.libraries.framework.extensions.handleBackPress
import mohsen.soltanian.cleanarchitecture.libraries.framework.receivers.ConnectionCheck
import timber.log.Timber

abstract class CoreFragment: Fragment(),ConnectionCheck.ConnectivityReceiverListener {


    private lateinit var connectionCheck: ConnectionCheck
    private lateinit var exitToast: Toast
    protected var me: AppCompatActivity? = null
    private var backPressedOnce = false
    var rootView: View? = null
    private var layout: Layout? = null
    private var handleBackPress: HandleBackPress? = null
    private var lockBackPress: LockBackPress? = null
    private var handleDoubleBackPress: HandleDoubleBackPress? = null
    private var checkNetworkConnection: CheckNetworkConnection? = null

    protected var layoutResId = -1

    open fun fragmentStart() {}

    open fun fragmentStop() {}

    open fun networkAvailable() {}

    open fun networkNotAvailable() {}

    fun firstTimeCreated(savedInstanceState: Bundle?) = savedInstanceState == null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is AppCompatActivity) {
            me = context
        }
        initAttributes()
    }

    private fun initAttributes() {
        layout = javaClass.getAnnotation(Layout::class.java)
        handleBackPress = javaClass.getAnnotation(HandleBackPress::class.java)
        lockBackPress = javaClass.getAnnotation(LockBackPress::class.java)
        handleDoubleBackPress = javaClass.getAnnotation(HandleDoubleBackPress::class.java)
        checkNetworkConnection = javaClass.getAnnotation(CheckNetworkConnection::class.java)

        if(layout == null) {
            throw Exception("Fragment layout is: Null")
        }else {
            if(layout!!.value == -1){
                throw Exception("Fragment layout value is: -1")
            }else {
                layoutResId = layout!!.value
            }
        }
        if(handleDoubleBackPress?.value == true) {
            handleBackPress {
                showMessageForDoubleBackPress()
            }
        }
        if(handleBackPress?.value == true) {
            handleBackPress{
                onBackPressedCallBack()
            }
        }
        if(lockBackPress?.value == true) {
            handleBackPress{
                Timber.tag("BackPress").i(": disable back press button")
            }
        }

        if (checkNetworkConnection?.value == true) {
            registerNetworkReceiver()
        }
    }

    protected fun showMessageForDoubleBackPress() {
        if (backPressedOnce) {
            me?.finish()
        }
        backPressedOnce = true
        exitToast = me?.exitToast(getString(R.string.app_exit_label))!!
        exitToast.apply { show() }
        lifecycleScope.launch {
            delay(2000)
            backPressedOnce = false
        }
    }

    override fun onStart() {
        super.onStart()
        fragmentStart()
    }

    override fun onStop() {
        super.onStop()
        if (checkNetworkConnection?.value == true) unregisterNetworkReceiver()
        fragmentStop()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        if(::exitToast.isInitialized) {
            exitToast.apply { cancel() }
        }
    }

    /**
     * Override onBackPressedCallBack in fragment
     */
    protected open fun onBackPressedCallBack() {}

    private fun registerNetworkReceiver() {
        connectionCheck = ConnectionCheck()
        connectionCheck.setConnectivityReceiverListener(this)
        me?.registerReceiver(
            connectionCheck,
            IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION)
        )
    }

    private fun unregisterNetworkReceiver() {
        try {
            me?.unregisterReceiver(connectionCheck)
        } catch (e: Throwable) {
            e.printStackTrace()
        }
    }


    /**
     * Return the [AppCompatActivity] this fragment is currently associated with.
     *
     * @throws IllegalStateException if not currently associated with an activity or if associated
     * only with a context.
     * @throws TypeCastException if the currently associated activity don't extend [AppCompatActivity].
     *
     * @see requireActivity
     */
    protected fun requireCompatActivity(): AppCompatActivity {
        requireActivity()
        val activity = requireActivity()
        if (activity is AppCompatActivity) {
            return activity
        } else {
            throw TypeCastException("Main activity should extend from 'AppCompatActivity'")
        }
    }

    protected fun checkArgument(argsKey: String): Boolean {
        return requireArguments().containsKey(argsKey)
    }

    override fun onNetworkConnectionChanged(isConnected: Boolean) {
        if (isConnected) {
            networkAvailable()
        } else {
            networkNotAvailable()
        }
    }
}
