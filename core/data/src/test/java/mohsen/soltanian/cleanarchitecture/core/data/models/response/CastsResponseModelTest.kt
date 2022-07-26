package mohsen.soltanian.cleanarchitecture.core.data.models.response

import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.verify
import mohsen.soltanian.cleanarchitecture.core.data.core.ModelTesting
import mohsen.soltanian.cleanarchitecture.core.data.core.ModelUnitTest
import org.amshove.kluent.`should equal`
import org.junit.Test

@ModelTesting(
    clazz = CastsResponse::class,
    modelFields = ["id", "cast"])
class CastsResponseModelTest : ModelUnitTest() {

    @MockK
    lateinit var castsResponse: CastsResponse

    @Test
    fun test() {
        every { castsResponse.id } returns 0L
        every { castsResponse.cast } returns listOf()

        castsResponse.id `should equal` 0L
        castsResponse.cast `should equal` listOf()

        verify(exactly = 1) {
            castsResponse.id
            castsResponse.cast
        }

    }
}