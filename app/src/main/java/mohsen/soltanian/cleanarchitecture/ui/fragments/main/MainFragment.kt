package mohsen.soltanian.cleanarchitecture.ui.fragments.main

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flowOn
import mohsen.soltanian.cleanarchitecture.BR
import mohsen.soltanian.cleanarchitecture.R
import mohsen.soltanian.cleanarchitecture.base.mvi.BaseMviFragment
import mohsen.soltanian.cleanarchitecture.databinding.FragmentMainBinding
import mohsen.soltanian.cleanarchitecture.libraries.framework.core.adapter.paging.PagingLoadStateAdapter
import mohsen.soltanian.cleanarchitecture.libraries.framework.core.base.annotation.HandleDoubleBackPress
import mohsen.soltanian.cleanarchitecture.libraries.framework.core.base.annotation.Layout
import mohsen.soltanian.cleanarchitecture.libraries.framework.extensions.asFlow
import mohsen.soltanian.cleanarchitecture.ui.fragments.main.adapter.MoviesAdapter
import mohsen.soltanian.cleanarchitecture.ui.fragments.main.adapter.SearchMoviesAdapter
import mohsen.soltanian.cleanarchitecture.ui.fragments.main.contract.MainPageContract

@SuppressLint("NonConstantResourceId")
@Layout(value = R.layout.fragment_main)
@HandleDoubleBackPress(value = true)
@AndroidEntryPoint
class MainFragment : BaseMviFragment<FragmentMainBinding, MainPageContract.State, MainViewModel>() {

    private val mainViewModel: MainViewModel by viewModels()

    private val moviesAdapter: MoviesAdapter = MoviesAdapter().apply {
        withLoadStateHeaderAndFooter(
            header = PagingLoadStateAdapter(adapter = this),
            footer = PagingLoadStateAdapter(adapter = this)
        )
        clickListener = { movie ->
            val direction =
                MainFragmentDirections.actionNavigationMainFragmentToNavigationMovieDetails(movie)
            findNavController().navigate(direction)
        }
    }

    private val searchAdapter: SearchMoviesAdapter = SearchMoviesAdapter().apply {
        clickListener = { movie ->
            val direction =
                MainFragmentDirections.actionNavigationMainFragmentToNavigationMovieDetails(movie)
            findNavController().navigate(direction)
        }
    }

    override fun fragmentStart() {
        super.fragmentStart()
        me?.supportActionBar?.title = "Movies List"
    }

    override val viewModel: MainViewModel
        get() = mainViewModel

    override fun bindingVariables(): HashMap<Int, Any> {
        val hashMap: HashMap<Int, Any> = HashMap() //define empty hashmap
        hashMap[BR.viewModel] = viewModel
        hashMap[BR.movieAdapter] = moviesAdapter
        hashMap[BR.searchAdapter] = searchAdapter
        hashMap[BR.rvLayoutManager] =
            StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL)
        hashMap[BR.searchRvLayoutManager] =
            StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL)
        return hashMap
    }

    override fun renderViewState(viewState: MainPageContract.State) {
        when (viewState) {
            is MainPageContract.State.MoviesData -> {
                moviesAdapter.submitData(lifecycle = lifecycle, pagingData = viewState.pagedData)
            }
        }
    }

    override fun onViewReady(bundle: Bundle?) {
        lifecycleScope.launchWhenStarted {
            getBinging()?.etSearchBox?.asFlow()?.debounce(500)?.filter { query ->
                if (query.isEmpty()) {
                    me?.runOnUiThread {
                        getBinging()?.etSearchBox?.setText("")
                        viewModel.showRv.set(true)
                    }
                    return@filter false
                } else {
                    return@filter true
                }
            }?.distinctUntilChanged()?.flowOn(IO)?.collect { value ->
                viewModel.searchQuery = value
                viewModel.onTriggerEvent(MainPageContract.Event.fetchSearchMovies)
            }
        }
    }
}