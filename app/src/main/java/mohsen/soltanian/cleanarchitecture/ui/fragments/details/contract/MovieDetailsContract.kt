package mohsen.soltanian.cleanarchitecture.ui.fragments.details.contract

import mohsen.soltanian.cleanarchitecture.core.data.models.response.CastsResponse

class MovieDetailsContract {

    sealed class Event {
        data class fetchMovieCast(val movieId: String = "") : MovieDetailsContract.Event()
    }

    sealed class State {
        data class MovieDetailData(val data: CastsResponse) : State()
    }
}