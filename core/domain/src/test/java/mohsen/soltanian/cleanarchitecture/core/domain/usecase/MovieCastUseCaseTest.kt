package mohsen.soltanian.cleanarchitecture.core.domain.usecase

import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.impl.annotations.SpyK
import io.mockk.mockk
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runTest
import mohsen.soltanian.cleanarchitecture.core.data.implClasses.ServiceImp
import mohsen.soltanian.cleanarchitecture.core.data.models.response.Cast
import mohsen.soltanian.cleanarchitecture.core.data.models.response.CastsResponse
import mohsen.soltanian.cleanarchitecture.core.data.services.RemoteApi.Companion.API_KEY
import mohsen.soltanian.cleanarchitecture.core.domain.core.DomainUnitTest
import org.amshove.kluent.`should be instance of`
import org.amshove.kluent.`should equal`
import org.junit.Test

internal class MovieCastUseCaseTest: DomainUnitTest() {


    @MockK lateinit var uesCase: MovieCastUseCase

    @MockK(relaxed = true) lateinit var castsModel: CastsResponse
    @MockK(relaxed = true) lateinit var params: MovieCastUseCase.Params

    @Test
    fun `run test for given empty cats List`()= runTest {
        coEvery {  uesCase.invoke(params = any()) } returns flow {
            emit(value = castsModel )
        }
        val response = uesCase.invoke(params = params)
        response.collect {
            it `should be instance of` CastsResponse::class.java
            it.id `should equal` 0L
            it.cast `should equal` emptyList()
        }

        coVerify(exactly = 1) { uesCase.invoke(any()) }
    }

}