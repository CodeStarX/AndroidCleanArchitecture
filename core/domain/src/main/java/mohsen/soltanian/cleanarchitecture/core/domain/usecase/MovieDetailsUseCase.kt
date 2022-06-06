package mohsen.soltanian.cleanarchitecture.core.domain.usecase

import kotlinx.coroutines.flow.FlowCollector
import mohsen.soltanian.cleanarchitecture.core.data.implClasses.ServiceImp
import mohsen.soltanian.cleanarchitecture.core.data.models.response.MoviesResponse
import mohsen.soltanian.cleanarchitecture.core.domain.base.BaseUseCase
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class MovieDetailsUseCase @Inject constructor(
    private val serviceImp: ServiceImp
): BaseUseCase<MovieDetailsUseCase.Params, MoviesResponse>() {

   data class Params(val id: Int)

    override suspend fun FlowCollector<MoviesResponse>.execute(params: Params) {
       TODO("Not yet implement")
    }
}