package mohsen.soltanian.cleanarchitecture.core.domain.usecase

import kotlinx.coroutines.flow.FlowCollector
import mohsen.soltanian.cleanarchitecture.core.data.implClasses.ServiceImp
import mohsen.soltanian.cleanarchitecture.core.data.models.response.MoviesResponse
import mohsen.soltanian.cleanarchitecture.core.data.services.RemoteApi.Companion.API_KEY
import mohsen.soltanian.cleanarchitecture.core.domain.base.BaseUseCase
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SearchMovieUseCase @Inject constructor(
    private val serviceImp: ServiceImp
) : BaseUseCase<SearchMovieUseCase.Params, MoviesResponse>() {

    data class Params(val query: String, val apikey: String = API_KEY)

    override suspend fun FlowCollector<MoviesResponse>.execute(params: Params) =
        emit(value = serviceImp.searchForMovies(query = params.query, apiKey = params.apikey))

}