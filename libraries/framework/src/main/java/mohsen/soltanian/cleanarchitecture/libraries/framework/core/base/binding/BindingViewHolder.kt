package mohsen.soltanian.cleanarchitecture.libraries.framework.core.base.binding

import android.content.Context
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView

/**
 * A Simple [BindingViewHolder] providing easier support for ViewBinding
 */
open class BindingViewHolder<VB : ViewDataBinding?>(val binding: VB) :
    RecyclerView.ViewHolder(binding!!.root) {
    val context: Context = binding!!.root.context
}