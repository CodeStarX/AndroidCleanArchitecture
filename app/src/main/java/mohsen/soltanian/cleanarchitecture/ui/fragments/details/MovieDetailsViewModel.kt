package mohsen.soltanian.cleanarchitecture.ui.fragments.details

import androidx.lifecycle.MutableLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import mohsen.soltanian.cleanarchitecture.core.data.models.response.Cast
import mohsen.soltanian.cleanarchitecture.core.domain.usecase.MovieCastUseCase
import mohsen.soltanian.cleanarchitecture.libraries.framework.core.base.mvi.MviViewModel
import mohsen.soltanian.cleanarchitecture.ui.fragments.details.contract.MovieDetailsContract
import javax.inject.Inject

@HiltViewModel
class MovieDetailsViewModel @Inject constructor(
    private val useCase: MovieCastUseCase
) : MviViewModel<MovieDetailsContract.State, MovieDetailsContract.Event>() {

    val list: MutableLiveData<List<Cast>> = MutableLiveData<List<Cast>>(emptyList())

    private var movieId: String = ""

    override fun onTriggerEvent(eventType: MovieDetailsContract.Event) {
        when (eventType) {
            is MovieDetailsContract.Event.fetchMovieCast -> {
                movieId = eventType.movieId
                fetchMovieCast()
            }
        }
    }

    private fun fetchMovieCast() = safeLaunch {
        callWithProgress(useCase(params = MovieCastUseCase.Params(movieId = movieId))) { data ->
            list.value = data.cast
        }
    }
}