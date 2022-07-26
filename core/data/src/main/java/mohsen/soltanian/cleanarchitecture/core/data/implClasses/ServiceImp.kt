package mohsen.soltanian.cleanarchitecture.core.data.implClasses

import androidx.annotation.VisibleForTesting
import mohsen.soltanian.cleanarchitecture.core.data.models.response.CastsResponse
import mohsen.soltanian.cleanarchitecture.core.data.models.response.MoviesResponse
import mohsen.soltanian.cleanarchitecture.core.data.scopes.ServerService
import mohsen.soltanian.cleanarchitecture.core.data.services.RemoteApi
import retrofit2.Retrofit
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ServiceImp
@Inject constructor(
    @ServerService var retrofit: Retrofit) : RemoteApi {
    private val api by lazy { retrofit.create(RemoteApi::class.java) }

    override suspend fun getMovies(sortBy: String, page: Int, apiKey: String): MoviesResponse =
        api.getMovies(sortBy = sortBy, page = page, apiKey = apiKey)

    override suspend fun getCasts(movieId: String, apiKey: String): CastsResponse =
        api.getCasts(movieId = movieId, apiKey = apiKey)

    override suspend fun searchForMovies(query: String, apiKey: String): MoviesResponse =
        api.searchForMovies(query = query, apiKey = apiKey)

}