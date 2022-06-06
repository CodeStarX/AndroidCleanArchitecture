package mohsen.soltanian.cleanarchitecture.ui.fragments.main.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import mohsen.soltanian.cleanarchitecture.BR
import mohsen.soltanian.cleanarchitecture.R
import mohsen.soltanian.cleanarchitecture.core.data.models.response.Movie
import mohsen.soltanian.cleanarchitecture.databinding.RowSearchMovieBinding
import mohsen.soltanian.cleanarchitecture.libraries.framework.core.adapter.BasicRecyclerAdapter
import mohsen.soltanian.cleanarchitecture.libraries.framework.core.base.annotation.Layout
import mohsen.soltanian.cleanarchitecture.libraries.framework.core.base.binding.getDataBinding

@SuppressLint("NonConstantResourceId")
@Layout(value = R.layout.row_search_movie)
class SearchMoviesAdapter : BasicRecyclerAdapter<Movie, RowSearchMovieBinding?>() {

    internal var clickListener: (Movie) -> Unit = { _ -> }

    override fun createBinding(inflater: LayoutInflater, parent: ViewGroup): RowSearchMovieBinding? {
        return getDataBinding(layoutInflater = inflater, container = parent)
    }

    override fun bindView(binding: RowSearchMovieBinding?, position: Int, item: Movie) {
        binding?.setVariable(BR.model, item)
        binding?.setVariable(BR.click, ClickProxy())
    }

    inner class ClickProxy {
        fun itemSelection(movie: Movie) {
            clickListener(movie)
        }
    }
}
