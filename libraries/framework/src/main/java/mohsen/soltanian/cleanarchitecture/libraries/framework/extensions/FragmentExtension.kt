package mohsen.soltanian.cleanarchitecture.libraries.framework.extensions

import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment

fun Fragment.handleBackPress(block:() -> Unit) {
    val callback: OnBackPressedCallback =
        object : OnBackPressedCallback(true /* enabled by default */) {
            override fun handleOnBackPressed() {
                block()
            }
        }
    activity?.onBackPressedDispatcher?.addCallback(this, callback)
}