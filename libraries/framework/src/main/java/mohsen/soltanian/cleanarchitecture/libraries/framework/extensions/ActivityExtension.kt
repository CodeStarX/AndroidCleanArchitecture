package mohsen.soltanian.cleanarchitecture.libraries.framework.extensions

import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity


fun AppCompatActivity.handleBackPress( block: () -> Unit) {
    val callback: OnBackPressedCallback =
        object : OnBackPressedCallback(true /* enabled by default */) {
            override fun handleOnBackPressed() {
                block()
            }
        }
    onBackPressedDispatcher.addCallback(this, callback)
}
