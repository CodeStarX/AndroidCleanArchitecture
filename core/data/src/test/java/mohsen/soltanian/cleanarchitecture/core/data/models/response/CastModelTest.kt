package mohsen.soltanian.cleanarchitecture.core.data.models.response

import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.verify
import mohsen.soltanian.cleanarchitecture.core.data.core.ModelTesting
import mohsen.soltanian.cleanarchitecture.core.data.core.ModelUnitTest
import org.amshove.kluent.`should equal`
import org.junit.Test

@ModelTesting(
    clazz = Cast::class,
    modelFields = ["name", "character", "profile_path"])
class CastModelTest : ModelUnitTest() {

    @MockK
    lateinit var castModel: Cast

    @Test
    fun `run test for Cast Model`() {
        every { castModel.name } returns "mohsen"
        castModel.name `should equal` "mohsen"

        verify(exactly = 1) { castModel.name }
    }
}