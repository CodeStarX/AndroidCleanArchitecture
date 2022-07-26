package mohsen.soltanian.cleanarchitecture.core.data.services

import mohsen.soltanian.cleanarchitecture.core.data.models.response.CastsResponse
import mohsen.soltanian.cleanarchitecture.core.data.models.response.MoviesResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface RemoteApi {

    companion object {
        const val API_KEY = "XXXXXXXXXXXXXXXXXXXXXXXX"
    }


    @GET("movie/{sort}")
    suspend fun getMovies(
        @Path("sort") sortBy: String,
        @Query("page") page: Int,
        @Query("api_key") apiKey: String
    ): MoviesResponse

    @GET("movie/{movieId}/credits")
    suspend fun getCasts(
        @Path("movieId") movieId: String,
        @Query("api_key") apiKey: String
    ): CastsResponse

    @GET("search/movie")
    suspend fun searchForMovies(
        @Query("query") query: String,
        @Query("api_key") apiKey: String
    ): MoviesResponse

}