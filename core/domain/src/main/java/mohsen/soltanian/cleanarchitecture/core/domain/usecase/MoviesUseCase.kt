package mohsen.soltanian.cleanarchitecture.core.domain.usecase

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import mohsen.soltanian.cleanarchitecture.core.data.implClasses.ServiceImp
import mohsen.soltanian.cleanarchitecture.core.data.models.response.Movie
import mohsen.soltanian.cleanarchitecture.core.data.services.RemoteApi.Companion.API_KEY
import mohsen.soltanian.cleanarchitecture.core.domain.base.FlowPagingUseCase
import mohsen.soltanian.cleanarchitecture.core.domain.pagingSource.MoviePagingSource
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MoviesUseCase @Inject constructor(
    private val serviceImp: ServiceImp
): FlowPagingUseCase<MoviesUseCase.Params, Movie>() {

    data class Params(val pagingConfig: PagingConfig, val sortBy: String, val apiKey: String = API_KEY)

    override fun execute(params: Params): Flow<PagingData<Movie>> {
        return Pager(config = params.pagingConfig,
            pagingSourceFactory = { MoviePagingSource(serviceImp= serviceImp, dataParams = params) }
        ).flow
    }


}