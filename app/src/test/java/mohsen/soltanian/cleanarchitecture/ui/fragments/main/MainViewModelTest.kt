package mohsen.soltanian.cleanarchitecture.ui.fragments.main

import androidx.paging.PagingData
import androidx.paging.map
import io.mockk.*
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.impl.annotations.SpyK
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runTest
import mohsen.soltanian.cleanarchitecture.core.AppUnitTest
import mohsen.soltanian.cleanarchitecture.core.data.models.response.Movie
import mohsen.soltanian.cleanarchitecture.core.data.models.response.MoviesResponse
import mohsen.soltanian.cleanarchitecture.core.domain.usecase.MoviesUseCase
import mohsen.soltanian.cleanarchitecture.core.domain.usecase.SearchMovieUseCase
import mohsen.soltanian.cleanarchitecture.ui.fragments.main.contract.MainPageContract
import org.amshove.kluent.`should be instance of`
import org.amshove.kluent.`should equal`
import org.junit.Test

class MainViewModelTest: AppUnitTest() {

    @MockK lateinit var moviesUseCase: MoviesUseCase
    @MockK lateinit var searchMovieUseCase: SearchMovieUseCase

    @SpyK
    @InjectMockKs
    lateinit var viewModel: MainViewModel

    @Test
    fun `verify onTriggerEvent method`() {
        viewModel.onTriggerEvent(eventType = MainPageContract.Event.fetchMovies)
        verify(exactly = 1) { viewModel.onTriggerEvent(any())}
    }

    @Test
    fun `fetch movies`() = runTest {
        val params = mockk<MoviesUseCase.Params>(relaxed = true)
        every { moviesUseCase.invoke(params = any()) } returns flow {
            emit(value = PagingData.empty())
        }

        viewModel.onTriggerEvent(eventType = MainPageContract.Event.fetchMovies)
        moviesUseCase.invoke(params = params).collect {
            it `should be instance of` PagingData::class.java
            it.map { model ->
                model `should be instance of` Movie::class.java
            }
        }

        verify {
            viewModel.onTriggerEvent(eventType = any())
            moviesUseCase.invoke(params = any())
        }
    }

    @Test
    fun `fetch search movie`() = runTest {
        val moviesResponse = mockk<MoviesResponse>(relaxed = true)
        val params = mockk<SearchMovieUseCase.Params>(relaxed = true)
        coEvery { searchMovieUseCase.invoke(params = any()) } returns flow {
            emit(value = moviesResponse)
        }

        viewModel.onTriggerEvent(eventType = MainPageContract.Event.fetchSearchMovies)
        searchMovieUseCase.invoke(params= params).collect {
            it `should be instance of` MoviesResponse::class.java
            it.page `should equal` 0
            it.results `should equal` listOf()
        }

        coVerify  {
            viewModel.onTriggerEvent(eventType = any())
            searchMovieUseCase.invoke(params = any())
        }
    }



}