package mohsen.soltanian.cleanarchitecture.ui.fragments.main.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import mohsen.soltanian.cleanarchitecture.BR
import mohsen.soltanian.cleanarchitecture.R
import mohsen.soltanian.cleanarchitecture.core.data.models.response.Movie
import mohsen.soltanian.cleanarchitecture.databinding.RowMovieBinding
import mohsen.soltanian.cleanarchitecture.libraries.framework.core.adapter.BasicPagingRecyclerAdapter
import mohsen.soltanian.cleanarchitecture.libraries.framework.core.base.annotation.Layout
import mohsen.soltanian.cleanarchitecture.libraries.framework.core.base.binding.getDataBinding

@SuppressLint("NonConstantResourceId")
@Layout(value = R.layout.row_movie)
class MoviesAdapter : BasicPagingRecyclerAdapter<Movie, RowMovieBinding?>(diffCallback = Comparator) {

    companion object Comparator : DiffUtil.ItemCallback<Movie>() {
        override fun areItemsTheSame(oldItem: Movie, newItem: Movie) =
            oldItem.movieId == newItem.movieId

        @SuppressLint("DiffUtilEquals")
        override fun areContentsTheSame(oldItem: Movie, newItem: Movie) =
            oldItem == newItem
    }
    internal var clickListener: (Movie) -> Unit = { _ -> }

    override fun createBinding(inflater: LayoutInflater, parent: ViewGroup): RowMovieBinding? {
        return getDataBinding(layoutInflater = inflater, container = parent)
    }

    override fun bindView(binding: RowMovieBinding?, position: Int, item: Movie) {
        binding?.setVariable(BR.model, item)
        binding?.setVariable(BR.click, ClickProxy())
    }

    inner class ClickProxy {
        fun itemSelection(movie: Movie) {
            clickListener(movie)
        }
    }
}
