package mohsen.soltanian.cleanarchitecture.ui.fragments.details

import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runTest
import mohsen.soltanian.cleanarchitecture.core.AppUnitTest
import mohsen.soltanian.cleanarchitecture.core.data.models.response.CastsResponse
import mohsen.soltanian.cleanarchitecture.core.domain.usecase.MovieCastUseCase
import mohsen.soltanian.cleanarchitecture.ui.fragments.details.contract.MovieDetailsContract
import org.amshove.kluent.`should be instance of`
import org.amshove.kluent.`should equal`
import org.junit.Test

class MovieDetailsViewModelTest: AppUnitTest() {

    @MockK lateinit var movieCastUseCase: MovieCastUseCase

    @MockK
    @InjectMockKs
    lateinit var viewModel: MovieDetailsViewModel

    @Test
    fun `verify onTriggerEvent method`() {
        val eventType = mockk<MovieDetailsContract.Event.fetchMovieCast>(relaxed = true)
        viewModel.onTriggerEvent(eventType = eventType )

        verify(exactly = 1) { viewModel.onTriggerEvent(eventType= any()) }
    }

    @Test
    fun `fetch Movie Cast`() = runTest {
        val params = mockk<MovieCastUseCase.Params>(relaxed = true)
        val castsResponse = mockk<CastsResponse>(relaxed = true)
        coEvery { movieCastUseCase.invoke(params= any()) } returns flow {
            emit(value = castsResponse)
        }

        viewModel.onTriggerEvent(eventType = MovieDetailsContract.Event.fetchMovieCast(movieId = "-1"))
        movieCastUseCase.invoke(params = params).collect {
            it `should be instance of` CastsResponse::class.java
            it.id `should equal` 0L
            it.cast `should equal` listOf()
        }

        coVerify {
            viewModel.onTriggerEvent(eventType = any())
            movieCastUseCase.invoke(params = any())
        }
    }

}