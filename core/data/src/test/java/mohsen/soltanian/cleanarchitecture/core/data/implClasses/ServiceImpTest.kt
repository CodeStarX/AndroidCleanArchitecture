package mohsen.soltanian.cleanarchitecture.core.data.implClasses

import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import mohsen.soltanian.cleanarchitecture.core.data.core.DataUnitTest
import mohsen.soltanian.cleanarchitecture.core.data.models.response.CastsResponse
import mohsen.soltanian.cleanarchitecture.core.data.models.response.MoviesResponse
import mohsen.soltanian.cleanarchitecture.core.data.services.RemoteApi.Companion.API_KEY
import org.amshove.kluent.`should be instance of`
import org.amshove.kluent.`should equal`
import org.junit.Test

class ServiceImpTest: DataUnitTest() {

    @MockK
    lateinit var serviceImp: ServiceImp

    @Test
    fun `get Movies list`() = runTest {
        val resModel = mockk<MoviesResponse>(relaxed = true)
        coEvery { serviceImp.getMovies(sortBy = any(), page = any(), apiKey = any()) } returns resModel
        val response = serviceImp.getMovies(sortBy = "popular", page = 1, apiKey = API_KEY)
        response `should be instance of` MoviesResponse::class.java
        response.page `should equal` 0
        response.results `should equal` listOf()

        coVerify(exactly = 1) { serviceImp.getMovies(sortBy = any(), page = any(), apiKey = any()) }
    }

    @Test
    fun `get Casts list`() = runTest() {
        val resModel = mockk<CastsResponse>(relaxed = true)
        coEvery { serviceImp.getCasts(movieId = any(), apiKey = any()) } returns resModel
        val response = serviceImp.getCasts(movieId = "0", apiKey = API_KEY)
        response `should be instance of`  CastsResponse::class.java
        response.id `should equal` 0L
        response.cast `should equal` listOf()

        coVerify(exactly = 1) { serviceImp.getCasts(movieId = any(), apiKey = any()) }

    }

    @Test
    fun `search movies by movie name`() = runTest() {
        val resModel = mockk<MoviesResponse>(relaxed = true)
        coEvery { serviceImp.searchForMovies(any(), any()) } returns resModel
        val response = serviceImp.searchForMovies("memory", API_KEY)
        response `should be instance of` MoviesResponse::class.java
        response.page `should equal` 0
        response.results `should equal` listOf()

        coVerify(exactly = 1) { serviceImp.searchForMovies(query = any(), apiKey =  any()) }

    }
}