package mohsen.soltanian.cleanarchitecture.core.domain.usecase

import kotlinx.coroutines.flow.FlowCollector
import mohsen.soltanian.cleanarchitecture.core.data.implClasses.ServiceImp
import mohsen.soltanian.cleanarchitecture.core.data.models.response.CastsResponse
import mohsen.soltanian.cleanarchitecture.core.data.services.RemoteApi.Companion.API_KEY
import mohsen.soltanian.cleanarchitecture.core.domain.base.BaseUseCase
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MovieCastUseCase @Inject constructor(
    private val serviceImp: ServiceImp
) : BaseUseCase<MovieCastUseCase.Params,CastsResponse>(){

    data class Params(val movieId: String, val apiKey: String = API_KEY)

    override suspend fun FlowCollector<CastsResponse>.execute(params: Params) =
        emit(value = serviceImp.getCasts(movieId = params.movieId, apiKey = params.apiKey))
}