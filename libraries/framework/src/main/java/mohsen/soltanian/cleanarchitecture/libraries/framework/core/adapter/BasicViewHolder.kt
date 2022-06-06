package mohsen.soltanian.cleanarchitecture.libraries.framework.core.adapter

import androidx.databinding.ViewDataBinding
import mohsen.soltanian.cleanarchitecture.libraries.framework.core.base.binding.BindingViewHolder

/**
 * A Simple [BasicViewHolder] providing easier support for ViewBinding
 */
class BasicViewHolder<VB : ViewDataBinding?>(binding: VB) : BindingViewHolder<VB>(binding)
