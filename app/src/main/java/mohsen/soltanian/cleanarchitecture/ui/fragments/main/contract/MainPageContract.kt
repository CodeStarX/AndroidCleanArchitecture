package mohsen.soltanian.cleanarchitecture.ui.fragments.main.contract

import androidx.paging.PagingData
import mohsen.soltanian.cleanarchitecture.core.data.models.response.Movie
import mohsen.soltanian.cleanarchitecture.core.data.models.response.MoviesResponse

class MainPageContract {
    sealed class Event {
        object fetchMovies : Event()
        object fetchSearchMovies : Event()
    }

    sealed class State {
        data class MoviesData(
            val pagedData: PagingData<Movie> =
                PagingData.empty()
        ) : State()

        object SearchMoviesData : State()
    }
}