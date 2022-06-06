package mohsen.soltanian.cleanarchitecture.libraries.framework.core.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil

@SuppressWarnings("TooManyFunctions")
abstract class BasicPagingRecyclerAdapter<T : Any, Binding : ViewDataBinding?>(diffCallback: DiffUtil.ItemCallback<T>) :
    PagingDataAdapter<T,BasicViewHolder<Binding?>>(diffCallback) {

    abstract fun createBinding(inflater: LayoutInflater, parent: ViewGroup): Binding?

    abstract fun bindView(binding: Binding, position: Int, item: T)

    /** Generates a ViewHolder from this Item with the given ViewDataBinding */
    open fun getViewHolder(viewBinding: Binding): BasicViewHolder<Binding?> {
        return BasicViewHolder(viewBinding)
    }

    /** Generates a ViewHolder from this Item with the given parent */
    private fun getViewHolder(parent: ViewGroup): BasicViewHolder<Binding?>? {
        return createBinding(LayoutInflater.from(parent.context), parent)?.let { getViewHolder(it) }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BasicViewHolder<Binding?> {
        return getViewHolder(parent)!!
    }

    override fun onBindViewHolder(holder: BasicViewHolder<Binding?>, position: Int) {
        holder.binding?.let { getItem(position)?.let { it1 -> bindView(it, position, item = it1) } }
    }

}