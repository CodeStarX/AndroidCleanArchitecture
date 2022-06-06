package mohsen.soltanian.cleanarchitecture.libraries.framework.binding

import android.graphics.drawable.Drawable
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import mohsen.soltanian.cleanarchitecture.libraries.framework.core.adapter.BasicRecyclerAdapter

object CommonBindingAdapter {
    @JvmStatic
    @BindingAdapter(value = ["imageUrl", "placeHolder"], requireAll = false)
    fun loadUrl(view: ImageView, url: String?, placeHolder: Drawable?) {
        Glide.with(view.context).load(url).placeholder(placeHolder).into(view)
    }

    @JvmStatic
    @BindingAdapter("data")
    fun setRecyclerViewProperties(recyclerView: RecyclerView, items: List<Any>) {
        if (recyclerView.adapter is BasicRecyclerAdapter<*, *>)
            (recyclerView.adapter as BasicRecyclerAdapter<*, *>).setItems(items as List<Nothing>)

    }

    @JvmStatic
    @BindingAdapter("visible")
    fun booleanToVisibility(view: View,value: Boolean?) =
        if (value == true) view.visibility = View.VISIBLE else view.visibility = View.GONE

}