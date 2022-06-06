package mohsen.soltanian.cleanarchitecture.core.domain.pagingSource

import androidx.paging.PagingSource
import androidx.paging.PagingState
import mohsen.soltanian.cleanarchitecture.core.data.implClasses.ServiceImp
import mohsen.soltanian.cleanarchitecture.core.data.models.response.Movie
import mohsen.soltanian.cleanarchitecture.core.domain.usecase.MoviesUseCase
import java.io.IOException

class MoviePagingSource(
    private val serviceImp: ServiceImp,
    private val dataParams: MoviesUseCase.Params
) : PagingSource<Int, Movie>() {

    override fun getRefreshKey(state: PagingState<Int, Movie>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Movie> {
        val page = params.key ?: 1
        return try {
            val response = serviceImp.getMovies(sortBy = dataParams.sortBy, page = page, apiKey = dataParams.apiKey)
            val list = response.results.orEmpty()

            LoadResult.Page(
                data = list,
                prevKey = if (page == 1) null else page - 1,
                nextKey = if (list.isEmpty()) null else page + 1
            )
        } catch (exception: IOException) {
            // IOException for network failures.
            return LoadResult.Error(exception)
        }
    }
}
