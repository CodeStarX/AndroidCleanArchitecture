package mohsen.soltanian.cleanarchitecture.libraries.framework.extensions

import android.content.Context
import android.content.res.Configuration
import android.net.ConnectivityManager
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.content.ContextCompat

fun Context.dp2px(value: Int): Int {
    val scale = resources.displayMetrics.density
    return (value.toFloat() * scale + 0.5f).toInt()
}

fun Context.drawable(@DrawableRes drawableRes: Int) =
    AppCompatResources.getDrawable(this@drawable, drawableRes)

fun Context.color(@ColorRes colorRes: Int) = ContextCompat.getColor(this@color, colorRes)

val Context.connectivityManager: ConnectivityManager
    get() =
        this.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager