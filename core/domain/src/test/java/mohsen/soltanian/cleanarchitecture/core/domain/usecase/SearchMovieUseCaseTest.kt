package mohsen.soltanian.cleanarchitecture.core.domain.usecase

import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runTest
import mohsen.soltanian.cleanarchitecture.core.data.models.response.MoviesResponse
import mohsen.soltanian.cleanarchitecture.core.domain.core.DomainUnitTest
import org.amshove.kluent.`should be instance of`
import org.amshove.kluent.`should equal`
import org.junit.Test

internal class SearchMovieUseCaseTest: DomainUnitTest() {

    @MockK lateinit var useCase: SearchMovieUseCase

    @MockK(relaxed = true) lateinit var params: SearchMovieUseCase.Params
    @MockK(relaxed = true) lateinit var moviesResponse: MoviesResponse

    @Test
    fun `run test`() = runTest() {
        coEvery { useCase.invoke(params = any()) } returns flow {
            emit(value = moviesResponse )
        }
        val response = useCase.invoke(params)
        response.collect {
            it `should be instance of` MoviesResponse::class.java
            it.page `should equal` 0
            it.results `should equal` listOf()
        }

        coVerify(exactly = 1) { useCase.invoke(any()) }

    }

}