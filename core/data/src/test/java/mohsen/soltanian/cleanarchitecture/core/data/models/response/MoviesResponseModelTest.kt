package mohsen.soltanian.cleanarchitecture.core.data.models.response

import io.mockk.mockk
import mohsen.soltanian.cleanarchitecture.core.data.core.ModelTesting
import mohsen.soltanian.cleanarchitecture.core.data.core.ModelUnitTest
import org.amshove.kluent.`should be instance of`
import org.amshove.kluent.`should equal`
import org.junit.Test

@ModelTesting(
    clazz = MoviesResponse::class,
    modelFields = ["page", "results"])
class MoviesResponseModelTest : ModelUnitTest() {

    @Test
    fun test() {
        val model = mockk<MoviesResponse>(relaxed = true)

        model `should be instance of` MoviesResponse::class.java
        model.page `should equal` 0
        model.results `should equal` listOf()

    }
}