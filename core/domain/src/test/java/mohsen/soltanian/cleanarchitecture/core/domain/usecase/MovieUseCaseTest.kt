package mohsen.soltanian.cleanarchitecture.core.domain.usecase

import androidx.paging.PagingData
import androidx.paging.map
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runTest
import mohsen.soltanian.cleanarchitecture.core.data.models.response.Movie
import mohsen.soltanian.cleanarchitecture.core.domain.core.DomainUnitTest
import org.amshove.kluent.`should be instance of`
import org.junit.Test

internal class MovieUseCaseTest : DomainUnitTest() {

    @MockK lateinit var uesCase: MoviesUseCase

    @MockK(relaxed = true) lateinit var params: MoviesUseCase.Params

    @Test
    fun `given empty movies list`() = runTest {
        coEvery { uesCase.invoke(params = any()) } returns flow {
            emit(value = PagingData.empty())
        }

        val response = uesCase.invoke(params = params)
        response.collect {
            it `should be instance of` PagingData::class.java
            it.map { model ->
                model `should be instance of` Movie::class.java
            }
        }

        coVerify(exactly = 1) { uesCase.invoke(params = any()) }

    }
}