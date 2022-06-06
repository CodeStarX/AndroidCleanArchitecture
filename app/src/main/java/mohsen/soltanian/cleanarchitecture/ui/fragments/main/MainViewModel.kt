package mohsen.soltanian.cleanarchitecture.ui.fragments.main

import androidx.databinding.ObservableBoolean
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import mohsen.soltanian.cleanarchitecture.core.data.models.response.Movie
import mohsen.soltanian.cleanarchitecture.core.domain.usecase.MoviesUseCase
import mohsen.soltanian.cleanarchitecture.core.domain.usecase.SearchMovieUseCase
import mohsen.soltanian.cleanarchitecture.libraries.framework.core.base.mvi.MviViewModel
import mohsen.soltanian.cleanarchitecture.ui.fragments.main.contract.MainPageContract
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val useCase: MoviesUseCase, private val searchMovieUseCase: SearchMovieUseCase
) : MviViewModel<MainPageContract.State, MainPageContract.Event>() {

    val searchList: MutableLiveData<List<Movie?>?> = MutableLiveData<List<Movie?>?>(emptyList())
    var searchQuery = ""
    private val config = PagingConfig(pageSize = 20)

    val progressBar: ObservableBoolean = ObservableBoolean(true)
    val showSearchBox: ObservableBoolean = ObservableBoolean(false)
    val showRv: ObservableBoolean = ObservableBoolean(true)

    init {
        onTriggerEvent(MainPageContract.Event.fetchMovies)
    }
    override fun onTriggerEvent(eventType: MainPageContract.Event) {
        when(eventType){
            is MainPageContract.Event.fetchMovies ->{
                fetchMovies()
            }
            is MainPageContract.Event.fetchSearchMovies -> {
                fetchSearchMovies()
            }
        }
    }

    private fun fetchMovies() = safeLaunch {
        val params = MoviesUseCase.Params(pagingConfig = config,
            sortBy = "popular")
        useCase(params = params)
            .cachedIn(scope = viewModelScope)
            .catch {
                progressBar.set(false)
                passError(it) }
            .collect {
                progressBar.set(false)
                showSearchBox.set(true)
                showRv.set(true)
                setState(MainPageContract.State.MoviesData(it))
            }
    }

    private fun fetchSearchMovies() = safeLaunch {
        callWithProgress(callFlow = searchMovieUseCase(params = SearchMovieUseCase.Params(query = searchQuery)), completionHandler = { data ->
            showRv.set(false)
            searchList.value = data.results
        })
    }
}