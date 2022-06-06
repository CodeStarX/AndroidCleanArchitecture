package mohsen.soltanian.cleanarchitecture.libraries.framework.extensions

import android.os.SystemClock
import android.text.Editable
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.EditText
import android.widget.ImageView
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow
import mohsen.soltanian.cleanarchitecture.libraries.framework.R

fun View.visible() {
    visibility = View.VISIBLE
}

fun View.gone() {
    visibility = View.GONE
}

fun View.invisible() {
    visibility = View.INVISIBLE
}


fun View.setSafeOnClickListener(debounceTime: Long = 600L, action: () -> Unit) {
    this.setOnClickListener(object : View.OnClickListener {
        private var lastClickTime: Long = 0

        override fun onClick(v: View) {
            if (SystemClock.elapsedRealtime() - lastClickTime < debounceTime) return
            else action()

            lastClickTime = SystemClock.elapsedRealtime()
        }
    })
}

fun ImageView.loadFromUrl(url: String) =
    Glide.with(this.context.applicationContext)
        .load(url)
        .transition(DrawableTransitionOptions.withCrossFade())
        .into(this)


fun EditText.asFlow() = callbackFlow {
    val afterTextChanged: (Editable?) -> Unit = { text ->
        this.trySend(text.toString()).isSuccess
    }

    val textChangedListener =
        addTextChangedListener(afterTextChanged = afterTextChanged)

    awaitClose {
        removeTextChangedListener(textChangedListener)
    }

    fun RecyclerView.runAnimation() {
        layoutAnimation =
            AnimationUtils.loadLayoutAnimation(context, R.anim.layout_animation_fall_down)
    }

}
