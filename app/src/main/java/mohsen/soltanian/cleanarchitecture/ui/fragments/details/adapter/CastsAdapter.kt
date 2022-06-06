package mohsen.soltanian.cleanarchitecture.ui.fragments.details.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import mohsen.soltanian.cleanarchitecture.BR
import mohsen.soltanian.cleanarchitecture.R
import mohsen.soltanian.cleanarchitecture.core.data.models.response.Cast
import mohsen.soltanian.cleanarchitecture.core.data.models.response.Movie
import mohsen.soltanian.cleanarchitecture.databinding.CastItemBinding
import mohsen.soltanian.cleanarchitecture.databinding.RowMovieBinding
import mohsen.soltanian.cleanarchitecture.libraries.framework.core.adapter.BasicPagingRecyclerAdapter
import mohsen.soltanian.cleanarchitecture.libraries.framework.core.adapter.BasicRecyclerAdapter
import mohsen.soltanian.cleanarchitecture.libraries.framework.core.base.annotation.Layout
import mohsen.soltanian.cleanarchitecture.libraries.framework.core.base.binding.getDataBinding

@SuppressLint("NonConstantResourceId")
@Layout(value = R.layout.cast_item)
class CastsAdapter : BasicRecyclerAdapter<Cast, CastItemBinding?>() {


    internal var clickListener: (Cast) -> Unit = { _ -> }

    override fun createBinding(inflater: LayoutInflater, parent: ViewGroup): CastItemBinding? {
        return getDataBinding(layoutInflater = inflater, container = parent)
    }

    override fun bindView(binding: CastItemBinding?, position: Int, item: Cast) {
        binding?.setVariable(BR.model, item)
        binding?.setVariable(BR.click, ClickProxy())
    }

    inner class ClickProxy {
        fun itemSelection(model: Cast) {
            clickListener(model)
        }
    }
}
