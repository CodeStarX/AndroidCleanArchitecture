package mohsen.soltanian.cleanarchitecture.core.domain.base

import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.spyk
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runTest
import mohsen.soltanian.cleanarchitecture.core.domain.core.DomainUnitTest
import org.amshove.kluent.`should be instance of`
import org.amshove.kluent.`should be`
import org.junit.Test
import java.lang.reflect.Parameter

class BaseUseCaseTest : DomainUnitTest() {

    @MockK lateinit var useCase: UseCaseSUT

    @MockK lateinit var params: Params
    @MockK lateinit var returnType: ReturnType

    @Test
    fun `running use case should return 'ReturnTypeClass'`() = runTest {
        every { params.name } returns "ParamTest"
        every { returnType.name } returns "mohsen"
        coEvery { useCase.invoke(params = any()) } returns flow {
            emit(value = returnType)
        }
        val response = useCase.invoke(params = params)
        response.collect {
            it `should be instance of` ReturnType::class.java
            it.name `should be` "mohsen"
        }

        coVerify(exactly = 1) {  useCase.invoke(params = any()) }
    }

    @Test
    fun `running use case should return 'ReturnTypeClass2'`() = runTest {
        every { params.name } returns "ParamTest"

        val __useCase = spyk<UseCaseSUT>()
        val response = __useCase.invoke(params = params)
        response.collect {
            it `should be instance of` ReturnType::class.java
            it.name `should be` "mohsen"
        }

        coVerify(exactly = 1) {  useCase.invoke(params = any()) }
    }

    data class Params(val name: String)
    data class ReturnType(val name: String)
    inner class UseCaseSUT : BaseUseCase<Params, ReturnType>() {
        override suspend fun FlowCollector<ReturnType>.execute(params: Params) {
            emit(value = ReturnType(name = "mohsen"))
        }

    }
}