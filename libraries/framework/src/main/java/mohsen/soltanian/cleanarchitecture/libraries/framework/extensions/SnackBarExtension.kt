package mohsen.soltanian.cleanarchitecture.libraries.framework.extensions

import android.app.Activity
import android.view.View
import android.view.ViewGroup
import androidx.annotation.IdRes
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar

fun Fragment.showSnackBar(view: View, message: String, @IdRes targetViewId: Int? = null) {
    Snackbar.make(view, message, Snackbar.LENGTH_LONG).apply {
        targetViewId?.let {
            anchorView = view.rootView.findViewById(it)
        }
        show()
    }
}

fun Activity.showSnackBar(view: View, message: String, @IdRes targetViewId: Int? = null) {
    Snackbar.make(view, message, Snackbar.LENGTH_LONG).apply {
        targetViewId?.let {
            anchorView = view.rootView.findViewById(it)
        }
        show()
    }
}
